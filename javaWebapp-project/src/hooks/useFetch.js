import { useEffect, useState } from 'react'

// Hook tái sử dụng cho mọi chỗ cần fetch data khi component mount
// Thay vì viết lại useState + useEffect + try/catch mỗi lần
//
// Cách dùng:
//   const { data, loading, error } = useFetch(() => getAllProducts())
//   const { data, loading, error } = useFetch(() => getProductById(id), [id])
//
// deps: mảng dependency — khi giá trị thay đổi, fetch lại (giống useEffect deps)

export function useFetch(fetchFn, deps = []) {
  const [data, setData]       = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError]     = useState(null)

  useEffect(() => {
    let cancelled = false  // tránh set state khi component đã unmount

    setLoading(true)
    setError(null)

    fetchFn()
      .then((res) => {
        if (!cancelled) setData(res.data)
      })
      .catch((err) => {
        if (!cancelled) setError(err.response?.data?.error || 'Đã có lỗi xảy ra')
      })
      .finally(() => {
        if (!cancelled) setLoading(false)
      })

    return () => { cancelled = true }
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, deps)

  return { data, loading, error }
}
