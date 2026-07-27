CREATE DATABASE QLBanHangOnline;
GO
USE QLBanHangOnline;
GO

-- 1. Người dùng (User)
CREATE TABLE [User] (
    [user_id] BIGINT PRIMARY KEY IDENTITY(1,1),
    full_name NVARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    [password] VARCHAR(255) NOT NULL,
    phone VARCHAR(15),
    [role] VARCHAR(20) DEFAULT 'CUSTOMER' CHECK([role] IN ('ADMIN', 'CUSTOMER')), -- 'ADMIN', 'CUSTOMER'
    [status] BIT DEFAULT 1,                -- 1: Active, 0: Blocked
    created_at DATETIME DEFAULT GETDATE()
);

-- 2. Thể loại (Category)
CREATE TABLE Category(
    category_id BIGINT PRIMARY KEY IDENTITY(1,1),
    [name] NVARCHAR(100) NOT NULL,
    [description] NVARCHAR(255)
);

-- 3. Thương hiệu (Brand)
CREATE TABLE Brand(
    brand_id BIGINT PRIMARY KEY IDENTITY(1,1),
    [name] NVARCHAR(100) NOT NULL
);

-- 4. Sản phẩm (Product)
CREATE TABLE [Product](
    product_id BIGINT PRIMARY KEY IDENTITY(1,1),
    category_id BIGINT,
    brand_id BIGINT,
    [name] NVARCHAR(200) NOT NULL,
    [description] NVARCHAR(MAX),
    [status] BIT DEFAULT 1,
    created_at DATETIME DEFAULT GETDATE(),

    CONSTRAINT FK_Product_Category FOREIGN KEY(category_id) REFERENCES Category(category_id) ON DELETE SET NULL,
    CONSTRAINT FK_Product_Brand FOREIGN KEY(brand_id) REFERENCES Brand(brand_id) ON DELETE SET NULL
);

-- 5. Ảnh sản phẩm (ProductImage)
CREATE TABLE ProductImage(
    image_id BIGINT PRIMARY KEY IDENTITY(1,1),
    product_id BIGINT NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    is_main BIT DEFAULT 0,
    
    CONSTRAINT FK_ProductImage_Product FOREIGN KEY (product_id) REFERENCES [Product](product_id) ON DELETE CASCADE
);

-- 6. Biến thể sản phẩm (Variant: Kích thước, Màu sắc, Giá, Tồn kho)
CREATE TABLE ProductVariant(
    variant_id BIGINT PRIMARY KEY IDENTITY(1,1),
    product_id BIGINT NOT NULL,
    size VARCHAR(10),
    color NVARCHAR(30),
    price DECIMAL(15,2) NOT NULL DEFAULT 0,
    stock INT NOT NULL DEFAULT 0 CHECK (stock >= 0),
    sku VARCHAR(50) UNIQUE, -- Stock keeping unit

    CONSTRAINT FK_Variant_Product FOREIGN KEY (product_id) REFERENCES [Product](product_id) ON DELETE CASCADE,
	CONSTRAINT UQ_ProductVariant 
	UNIQUE(product_id,size,color)-- Chống trùng 
);

-- 7. Giỏ hàng (Cart)
CREATE TABLE Cart(
    cart_id BIGINT PRIMARY KEY IDENTITY(1,1),
    [user_id] BIGINT NOT NULL UNIQUE,
    created_at DATETIME DEFAULT GETDATE(),

    CONSTRAINT FK_Cart_User FOREIGN KEY ([user_id]) REFERENCES [User]([user_id]) ON DELETE CASCADE
);

-- 8. Sản phẩm trong giỏ hàng (CartItem)
CREATE TABLE CartItem(
    cart_item_id BIGINT PRIMARY KEY IDENTITY(1,1),
    cart_id BIGINT NOT NULL,
    variant_id BIGINT NOT NULL,
    quantity INT NOT NULL DEFAULT 1 CHECK (quantity > 0),

    CONSTRAINT FK_CartItem_Cart FOREIGN KEY (cart_id) REFERENCES Cart(cart_id) ON DELETE CASCADE,
    CONSTRAINT FK_CartItem_Variant FOREIGN KEY (variant_id) REFERENCES ProductVariant(variant_id) ON DELETE CASCADE,
	UNIQUE(cart_id,variant_id) -- Nếu trong giỏ mà khách mua 2 sản phẩm giống nhau thì chỉ cập nhật số lượng 
);

-- 9. Địa chỉ giao hàng (Address)
CREATE TABLE [Address](
    address_id BIGINT PRIMARY KEY IDENTITY(1,1),
    [user_id] BIGINT NOT NULL,
    receiver_name NVARCHAR(100) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    province NVARCHAR(100) NOT NULL,
    district NVARCHAR(100) NOT NULL,
    ward NVARCHAR(100) NOT NULL,
    detail_address NVARCHAR(255) NOT NULL,
    is_default BIT DEFAULT 0,

    CONSTRAINT FK_Address_User FOREIGN KEY ([user_id]) REFERENCES [User]([user_id]) ON DELETE CASCADE
);

-- 10. Đơn hàng (Order)
CREATE TABLE [Order](
    order_id BIGINT PRIMARY KEY IDENTITY(1,1),
    [user_id] BIGINT NOT NULL,
    address_id BIGINT,
    total_amount DECIMAL(15,2) NOT NULL,
    shipping_fee DECIMAL(15,2) DEFAULT 0,
    note NVARCHAR(255),
    [status] VARCHAR(30) DEFAULT 'PENDING' CHECK(status IN('PENDING', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED')), -- 'PENDING', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED'
    created_at DATETIME DEFAULT GETDATE(),

    CONSTRAINT FK_Order_User FOREIGN KEY ([user_id]) REFERENCES [User]([user_id]),
    CONSTRAINT FK_Order_Address FOREIGN KEY (address_id) REFERENCES [Address](address_id),
	
);

-- 11. Chi tiết đơn hàng (OrderDetail)
CREATE TABLE OrderDetail(
    order_detail_id BIGINT PRIMARY KEY IDENTITY(1,1),
    order_id BIGINT NOT NULL,
    variant_id BIGINT NOT NULL,
    price DECIMAL(15,2) NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    subtotal DECIMAL(15,2) NOT NULL,

    CONSTRAINT FK_OrderDetail_Order FOREIGN KEY (order_id) REFERENCES [Order](order_id) ON DELETE CASCADE,
    CONSTRAINT FK_OrderDetail_Variant FOREIGN KEY (variant_id) REFERENCES ProductVariant(variant_id)
);

-- 12. Thanh toán (Payment)
CREATE TABLE Payment(
    payment_id BIGINT PRIMARY KEY IDENTITY(1,1),
    order_id BIGINT NOT NULL UNIQUE,
    method VARCHAR(30) NOT NULL 	CHECK(method IN('COD','VNPAY','BANK_TRANSFER')), -- 'COD', 'VNPAY', 'BANK_TRANSFER'
    [status] VARCHAR(30) DEFAULT 'PENDING' 	CHECK(status IN('SUCCESS', 'FAILED', 'PENDING')), -- 'PENDING', 'SUCCESS', 'FAILED'
    paid_at DATETIME,
    
    CONSTRAINT FK_Payment_Order FOREIGN KEY (order_id) REFERENCES [Order](order_id) ON DELETE CASCADE,
);

-- 13. Đánh giá (Review)
CREATE TABLE Review(
    review_id BIGINT PRIMARY KEY IDENTITY(1,1),
    [user_id] BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    comment NVARCHAR(MAX),
    created_at DATETIME DEFAULT GETDATE(),

    CONSTRAINT FK_Review_User FOREIGN KEY ([user_id]) REFERENCES [User]([user_id]),
    CONSTRAINT FK_Review_Product FOREIGN KEY (product_id) REFERENCES [Product](product_id) ON DELETE CASCADE
);
