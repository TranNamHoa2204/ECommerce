// App JS - Dynamic Fetch with API Fallback

let cartCount = 0;

const mockProducts = [
    { productId: 1, name: "Tai nghe Bluetooth Wireless Pro", categoryName: "Âm thanh", price: 1290000, imageUrl: "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500&q=80" },
    { productId: 2, name: "Đồng hồ Smartwatch Ultra 2", categoryName: "Đồng hồ", price: 3490000, imageUrl: "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=500&q=80" },
    { productId: 3, name: "Bàn phím Cơ Gaming RGB", categoryName: "Phụ kiện", price: 1850000, imageUrl: "https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=500&q=80" },
    { productId: 4, name: "Chuột Không Dây Ergonomic", categoryName: "Phụ kiện", price: 890000, imageUrl: "https://images.unsplash.com/photo-1615663245857-ac93bb7c39e7?w=500&q=80" },
    { productId: 5, name: "Camera Hành Trình 4K HDR", categoryName: "Thiết bị số", price: 2590000, imageUrl: "https://images.unsplash.com/photo-1526170375885-4d8ecf77b99f?w=500&q=80" },
    { productId: 6, name: "Loa Bluetooth Bass Pro 360", categoryName: "Âm thanh", price: 2190000, imageUrl: "https://images.unsplash.com/photo-1608043152269-423dbba4e7e1?w=500&q=80" }
];

document.addEventListener("DOMContentLoaded", () => {
    loadCategories();
    loadProducts();
});

async function loadCategories() {
    const container = document.getElementById("categoryTags");
    try {
        const response = await fetch("/api/categories");
        if (response.ok) {
            const categories = await response.json();
            if (categories.length > 0) {
                container.innerHTML = `<button class="tag active" onclick="filterCategory(this, 'all')">Tất cả</button>`;
                categories.forEach(cat => {
                    container.innerHTML += `<button class="tag" onclick="filterCategory(this, ${cat.categoryId})">${cat.name}</button>`;
                });
                return;
            }
        }
    } catch (e) {
        console.log("API server fallback mode");
    }

    // Default static tags
    container.innerHTML = `
        <button class="tag active">Tất cả</button>
        <button class="tag">Âm thanh</button>
        <button class="tag">Đồng hồ</button>
        <button class="tag">Phụ kiện</button>
        <button class="tag">Thiết bị số</button>
    `;
}

async function loadProducts(keyword = "") {
    const grid = document.getElementById("productsGrid");
    let products = [];

    try {
        let url = "/api/products";
        if (keyword) {
            url = `/api/products/search?keyword=${encodeURIComponent(keyword)}`;
        }
        const response = await fetch(url);
        if (response.ok) {
            products = await response.json();
        }
    } catch (e) {
        console.log("Dùng danh sách mẫu thử nghiệm UI");
    }

    if (!products || products.length === 0) {
        products = mockProducts;
    }

    renderProducts(products);
}

function renderProducts(products) {
    const grid = document.getElementById("productsGrid");
    grid.innerHTML = "";

    products.forEach(p => {
        const priceFormatted = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(p.price || 1200000);
        const image = p.imageUrl || "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500&q=80";

        grid.innerHTML += `
            <div class="product-card">
                <div class="product-img-wrapper">
                    <img src="${image}" alt="${p.name}">
                </div>
                <div>
                    <div class="product-category">${p.categoryName || 'Công nghệ'}</div>
                    <div class="product-title">${p.name}</div>
                </div>
                <div class="product-bottom">
                    <div class="product-price">${priceFormatted}</div>
                    <button class="btn-add-cart" onclick="addToCart('${p.name}')">+ Thêm giỏ</button>
                </div>
            </div>
        `;
    });
}

function addToCart(productName) {
    cartCount++;
    document.getElementById("cartBadge").innerText = cartCount;
    showToast(`Đã thêm "${productName}" vào giỏ hàng!`);
}

function showToast(message) {
    const toast = document.getElementById("toast");
    toast.innerText = message;
    toast.classList.add("show");
    setTimeout(() => {
        toast.classList.remove("show");
    }, 2500);
}

function filterCategory(btn, catId) {
    document.querySelectorAll(".tag").forEach(t => t.classList.remove("active"));
    btn.classList.add("active");
    loadProducts();
}
