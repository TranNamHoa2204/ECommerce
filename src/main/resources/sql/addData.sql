USE QLBanHangOnline;
GO

-- ============================================================================
-- NGUYÊN TẮC QUAN TRỌNG KHI CHÈN DỮ LIỆU (INSERT DATA):
-- 1. Thứ tự chèn: Chèn bảng KHÔNG CÓ khóa ngoại (FK) trước -> Sau đó đến các bảng CÓ khóa ngoại.
--    Ví dụ: Phải chèn User, Category, Brand trước rồi mới chèn Product, Cart, Address...
-- 2. Tiếng Việt có dấu: Với cột kiểu NVARCHAR, cần ghi tiền tố N trước chuỗi. Ví dụ: N'Áo thun'
-- 3. Kiểu dữ liệu BIT: 1 (True / Active / Default), 0 (False / Blocked / Normal)
-- 4. Tên bảng trùng từ khóa (User, Product, Address, Order): Nên bọc trong dấu ngoặc vuông []
-- ============================================================================

-- ----------------------------------------------------------------------------
-- BƯỚC 1: Thêm dữ liệu vào các bảng độc lập (User, Category, Brand)
-- ----------------------------------------------------------------------------

-- 1. Bảng User (Người dùng)
INSERT INTO [User] (full_name, email, [password], phone, [role], [status]) 
VALUES 
(N'Quản Trị Viên', 'admin@gmail.com', 'admin123', '0901234567', 'ADMIN', 1),
(N'Nguyễn Văn A', 'nguyenvana@gmail.com', '123456', '0912345678', 'CUSTOMER', 1),
(N'Trần Thị B', 'tranthib@gmail.com', '123456', '0987654321', 'CUSTOMER', 1);

-- 2. Bảng Category (Thể loại)
INSERT INTO Category ([name], [description])
VALUES 
(N'Áo phông', N'Các sản phẩm áo phông'),
(N'Áo polo', N'Các sản phẩm áo polo'),
(N'Áo sơ mi', N'Các sản phẩm áo sơ mi'),
(N'Áo khoác', N'Các sản phẩm áo khoác'),
(N'Quần short', N'Các sản phẩm quần short'),
(N'Quần dài', N'Các sản phẩm quần dài');

-- 3. Bảng Brand (Thương hiệu)
INSERT INTO Brand ([name])
VALUES 
(N'Canifa'),
(N'Việt Tiến'),
(N'Uniqlo'),
(N'Coolmate');

-- ----------------------------------------------------------------------------
-- BƯỚC 2: Thêm dữ liệu vào các bảng phụ thuộc cấp 1 (Product, Cart, Address)
-- ----------------------------------------------------------------------------

-- 4. Bảng Product (Sản phẩm - Phụ thuộc Category & Brand)
-- category_id: 1 (Áo Nam), 3 (Giày Thể Thao)
-- brand_id: 4 (Coolmate), 1 (Nike)
INSERT INTO [Product] (category_id, brand_id, [name], [description], [status])
VALUES 
(1, 4, N'Áo Thun Nam Coolmate Cotton', N'Áo thun chất liệu cotton thoáng mát, thấm hút mồ hôi tốt', 1),
(3, 1, N'Giày Chạy Bộ Nike Air Zoom', N'Giày chạy bộ êm ái, hỗ trợ vận động thể thao đỉnh cao', 1);

-- 5. Bảng Cart (Giỏ hàng - Phụ thuộc User)
-- Chèn giỏ hàng cho user_id = 2 và user_id = 3
INSERT INTO Cart ([user_id])
VALUES 
(2),
(3);

-- 6. Bảng Address (Địa chỉ giao hàng - Phụ thuộc User)
INSERT INTO [Address] ([user_id], receiver_name, phone, province, district, ward, detail_address, is_default)
VALUES 
(2, N'Nguyễn Văn A', '0912345678', N'TP. Hồ Chí Minh', N'Quận 1', N'Phường Bến Nghé', N'123 Đường Lê Lợi', 1),
(3, N'Trần Thị B', '0987654321', N'Hà Nội', N'Quận Cầu Giấy', N'Phường Dịch Vọng', N'45 Đường Xuân Thủy', 1);

-- ----------------------------------------------------------------------------
-- BƯỚC 3: Thêm dữ liệu vào các bảng phụ thuộc cấp 2 (ProductImage, ProductVariant)
-- ----------------------------------------------------------------------------

-- 7. Bảng ProductImage (Hình ảnh sản phẩm - Phụ thuộc Product)
-- product_id: 1 (Áo Thun), 2 (Giày Nike)
INSERT INTO ProductImage (product_id, image_url, is_main)
VALUES 
(1, '/images/products/ao-thun-coolmate-1.jpg', 1),
(1, '/images/products/ao-thun-coolmate-2.jpg', 0),
(2, '/images/products/nike-air-zoom-1.jpg', 1);

-- 8. Bảng ProductVariant (Biến thể sản phẩm - Phụ thuộc Product)
INSERT INTO ProductVariant (product_id, size, color, price, stock, sku)
VALUES 
(1, 'L', N'Đen', 199000.00, 50, 'COOL-AT-DEN-L'),
(1, 'M', N'Trắng', 199000.00, 30, 'COOL-AT-TRANG-M'),
(2, '42', N'Xanh Dương', 2500000.00, 15, 'NIKE-AZ-BLUE-42');

-- ----------------------------------------------------------------------------
-- BƯỚC 4: Thêm dữ liệu vào các bảng giao dịch & chi tiết (CartItem, Order, OrderDetail, Payment, Review)
-- ----------------------------------------------------------------------------

-- 9. Bảng CartItem (Sản phẩm trong giỏ - Phụ thuộc Cart & ProductVariant)
-- cart_id: 1 (Giỏ của User 2), variant_id: 1 (Áo đen size L)
INSERT INTO CartItem (cart_id, variant_id, quantity)
VALUES 
(1, 1, 2);

-- 10. Bảng Order (Đơn hàng - Phụ thuộc User & Address)
INSERT INTO [Order] ([user_id], address_id, total_amount, shipping_fee, note, [status])
VALUES 
(2, 1, 398000.00, 30000.00, N'Giao giờ hành chính', 'PENDING');

-- 11. Bảng OrderDetail (Chi tiết đơn hàng - Phụ thuộc Order & ProductVariant)
-- order_id: 1, variant_id: 1, số lượng: 2, đơn giá: 199,000 => subtotal: 398,000
INSERT INTO OrderDetail (order_id, variant_id, price, quantity, subtotal)
VALUES 
(1, 1, 199000.00, 2, 398000.00);

-- 12. Bảng Payment (Thanh toán - Phụ thuộc Order)
INSERT INTO Payment (order_id, method, [status], paid_at)
VALUES 
(1, 'COD', 'PENDING', NULL);

-- 13. Bảng Review (Đánh giá sản phẩm - Phụ thuộc User & Product)
INSERT INTO Review ([user_id], product_id, rating, comment)
VALUES 
(2, 1, 5, N'Sản phẩm rất đẹp, vải mát và vừa vặn!');
