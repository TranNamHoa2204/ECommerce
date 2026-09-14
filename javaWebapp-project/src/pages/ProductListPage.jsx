import { useEffect, useState } from 'react'
import { getAllCategories } from '../api/categoryApi'
import {
  getAllProducts,
  getProductsByCategory,
  searchProducts,
} from '../api/productApi'
import ErrorMessage from '../components/common/ErrorMessage'
import LoadingSpinner from '../components/common/LoadingSpinner'
import ProductCard from '../components/product/ProductCard'

export default function ProductListPage() {
  const [products, setProducts] = useState([])
  const [categories, setCategories] = useState([])
  const [activeCat, setActiveCat] = useState(null)
  const [keyword, setKeyword] = useState('')
  const [priceMap, setPriceMap] = useState({})
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    getAllCategories()
      .then((res) => setCategories(res.data))
      .catch(() => {})
  }, [])

  useEffect(() => {
    setLoading(true)
    setError(null)

    const request = activeCat
      ? getProductsByCategory(activeCat)
      : getAllProducts()

    request
      .then((res) => {
        const prods = res.data
        setProducts(prods)
        setPriceMap(buildPriceMap(prods))
      })
      .catch((err) => setError(err.response?.data?.error || 'Không thể tải sản phẩm'))
      .finally(() => setLoading(false))
  }, [activeCat])

  const displayed = keyword.trim()
    ? products.filter((p) =>
        p.name.toLowerCase().includes(keyword.toLowerCase())
      )
    : products

  const handleSearch = (e) => {
    if (e.key === 'Enter' && keyword.trim()) {
      setLoading(true)
      setError(null)

      searchProducts(keyword)
        .then((res) => {
          const prods = res.data
          setProducts(prods)
          setActiveCat(null)
          setPriceMap(buildPriceMap(prods))
        })
        .catch((err) => setError(err.response?.data?.error || 'Lỗi tìm kiếm'))
        .finally(() => setLoading(false))
    }

    if (e.key === 'Backspace' && keyword.length === 1) {
      setKeyword('')
      setActiveCat(null)
    }
  }

  return (
    <div>
      <div style={styles.header}>
        <h1 style={styles.title}>Tất cả sản phẩm</h1>

        <input
          type="text"
          placeholder="Tìm kiếm sản phẩm... (Enter để tìm)"
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
          onKeyDown={handleSearch}
          style={styles.searchInput}
        />
      </div>

      <div style={styles.filterRow}>
        <button
          onClick={() => { setActiveCat(null); setKeyword('') }}
          style={{ ...styles.filterBtn, ...(activeCat === null ? styles.filterBtnActive : {}) }}
        >
          Tất cả
        </button>

        {categories.map((cat) => (
          <button
            key={cat.categoryId}
            onClick={() => { setActiveCat(cat.categoryId); setKeyword('') }}
            style={{
              ...styles.filterBtn,
              ...(activeCat === cat.categoryId ? styles.filterBtnActive : {}),
            }}
          >
            {cat.name}
          </button>
        ))}
      </div>

      {loading && <LoadingSpinner />}
      {error && <ErrorMessage message={error} />}

      {!loading && !error && (
        <>
          <p style={styles.count}>{displayed.length} sản phẩm</p>

          {displayed.length === 0 ? (
            <p style={styles.empty}>Không tìm thấy sản phẩm nào.</p>
          ) : (
            <div style={styles.grid}>
              {displayed.map((p) => (
                <ProductCard
                  key={p.productId}
                  product={p}
                  price={priceMap[p.productId]}
                  mainImage={getMainImage(p)}
                />
              ))}
            </div>
          )}
        </>
      )}
    </div>
  )
}

function buildPriceMap(products) {
  return products.reduce((map, product) => {
    const prices = (product.variants ?? []).map((variant) => variant.price)
    map[product.productId] = prices.length ? Math.min(...prices) : null
    return map
  }, {})
}

function getMainImage(product) {
  const images = product.images ?? []
  return (images.find((image) => image.main) ?? images[0])?.imageUrl ?? null
}

const styles = {
  header: {
    display: 'flex', justifyContent: 'space-between', alignItems: 'center',
    marginBottom: '20px', gap: '16px', flexWrap: 'wrap',
  },
  title: { fontSize: '1.5rem', fontWeight: 700 },
  searchInput: {
    padding: '8px 14px', borderRadius: '8px', border: '1px solid #d1d5db',
    fontSize: '0.95rem', width: '280px', outline: 'none',
  },
  filterRow: { display: 'flex', gap: '8px', flexWrap: 'wrap', marginBottom: '24px' },
  filterBtn: {
    padding: '6px 16px', borderRadius: '20px', border: '1px solid #d1d5db',
    background: '#fff', cursor: 'pointer', fontSize: '0.875rem', color: '#374151',
    transition: 'all 0.15s',
  },
  filterBtnActive: {
    background: '#111827', color: '#fff', borderColor: '#111827',
  },
  count: { color: '#6b7280', fontSize: '0.875rem', marginBottom: '16px' },
  empty: { textAlign: 'center', padding: '60px', color: '#9ca3af' },
  grid: {
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fill, minmax(200px, 1fr))',
    gap: '20px',
  },
}
