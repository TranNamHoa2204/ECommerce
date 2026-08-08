import sys
import zipfile
import re
import xml.etree.ElementTree as ET

sys.stdout.reconfigure(encoding='utf-8')

def parse_sheet(path):
    with zipfile.ZipFile(path, 'r') as z:
        strings = []
        if 'xl/sharedStrings.xml' in z.namelist():
            tree = ET.fromstring(z.read('xl/sharedStrings.xml'))
            for elem in tree.iter():
                if elem.tag.endswith('t'):
                    strings.append(elem.text or '')
        tree = ET.fromstring(z.read('xl/worksheets/sheet1.xml'))
        rows = []
        for r in tree.iter():
            if r.tag.endswith('row'):
                row_data = []
                for c in r.iter():
                    if c.tag.endswith('c'):
                        t = c.attrib.get('t')
                        v = ''
                        for val in c.iter():
                            if val.tag.endswith('v'):
                                v = val.text or ''
                        if t == 's' and v.isdigit():
                            idx = int(v)
                            v = strings[idx] if idx < len(strings) else v
                        row_data.append(v)
                if row_data:
                    rows.append(row_data)
        return rows

prod_rows = parse_sheet(r'q:\DOWNLOAD2\ECommerce\src\main\webapp\images\Product_Corrected.xlsx')
var_rows = parse_sheet(r'q:\DOWNLOAD2\ECommerce\src\main\webapp\images\ProductVariants_Corrected.xlsx')
img_rows = parse_sheet(r'q:\DOWNLOAD2\ECommerce\src\main\webapp\images\ProductImage.xlsx')

sql_lines = []
sql_lines.append('USE QLBanHangOnline;\nGO\n')
sql_lines.append('-- ============================================================================')
sql_lines.append('-- DỮ LIỆU ĐÃ ĐƯỢC CHUẨN HÓA VÀ LÀM SẠCH TỪ 3 FILE EXCEL')
sql_lines.append('-- (Product_Corrected.xlsx, ProductVariants_Corrected.xlsx, ProductImage.xlsx)')
sql_lines.append('-- ============================================================================\n')

# 1. USER
sql_lines.append('-- 1. Bảng User')
sql_lines.append("""INSERT INTO [User] (full_name, email, [password], phone, [role], [status]) 
VALUES 
(N'Quản Trị Viên', 'admin@gmail.com', 'admin123', '0901234567', 'ADMIN', 1),
(N'Nguyễn Văn A', 'nguyenvana@gmail.com', '123456', '0912345678', 'CUSTOMER', 1),
(N'Trần Thị B', 'tranthib@gmail.com', '123456', '0987654321', 'CUSTOMER', 1);
""")

# 2. CATEGORY
sql_lines.append('-- 2. Bảng Category (Thể loại)')
sql_lines.append('SET IDENTITY_INSERT Category ON;')
sql_lines.append("""INSERT INTO Category (category_id, [name], [description])
VALUES 
(1, N'Áo phông', N'Các sản phẩm áo phông cotton, unisex, trẻ trung'),
(2, N'Áo polo', N'Các sản phẩm áo polo nam phối màu, lịch sự'),
(3, N'Áo sơ mi', N'Các sản phẩm áo sơ mi cotton ngắn tay, dài tay'),
(4, N'Áo khoác', N'Các sản phẩm áo khoác gió, chống nắng, lông vũ'),
(5, N'Quần short', N'Các sản phẩm quần soóc nam, bermuda, túi hộp'),
(6, N'Quần dài', N'Các sản phẩm quần dài, leggings, quần khaki');
""")
sql_lines.append('SET IDENTITY_INSERT Category OFF;\n')

# 3. BRAND
sql_lines.append('-- 3. Bảng Brand (Thương hiệu)')
sql_lines.append('SET IDENTITY_INSERT Brand ON;')
sql_lines.append("""INSERT INTO Brand (brand_id, [name])
VALUES 
(1, N'Canifa'),
(2, N'Việt Tiến'),
(3, N'Uniqlo'),
(4, N'Coolmate');
""")
sql_lines.append('SET IDENTITY_INSERT Brand OFF;\n')

# 4. PRODUCT
sql_lines.append('-- 4. Bảng Product (Sản phẩm)')
sql_lines.append('SET IDENTITY_INSERT [Product] ON;')

price_map = {}
prod_inserts = []
for r in prod_rows[1:]:
    pid = int(r[0])
    try:
        price = float(r[3])
    except:
        price = 199000.0
    price_map[pid] = price
    
    cat_name = r[5] if len(r) > 5 else 'Áo Phông'
    name_check = (r[1] + ' ' + (r[4] if len(r)>4 else '')).lower()
    
    if 'sơ mi' in name_check:
        cat_id = 3
    elif 'khoác' in name_check or 'lông vũ' in name_check or 'măng tô' in name_check:
        cat_id = 4
    elif 'polo' in name_check:
        cat_id = 2
    elif 'soóc' in name_check or 'short' in name_check or 'bermuda' in name_check:
        cat_id = 5
    elif 'quần' in name_check or 'leggings' in name_check or 'khaki' in name_check or 'jeans' in name_check:
        cat_id = 6
    else:
        cat_id = 1
        
    brand_id = 1 # Canifa
    name = r[1]
    if name in ['Status', 'Canifa', 'Áo Polo', 'Quần shorts', 'Quần dài', 'Áo khoác', 'Áo sơ mi']:
        name = r[4] if len(r) > 4 else 'Sản phẩm Canifa'
    
    name_clean = name.replace("'", "''")
    desc_clean = f"Sản phẩm {name_clean} chính hãng chất lượng cao.".replace("'", "''")
    
    prod_inserts.append(f"({pid}, {cat_id}, {brand_id}, N'{name_clean}', N'{desc_clean}', 1)")

sql_lines.append('INSERT INTO [Product] (product_id, category_id, brand_id, [name], [description], [status]) VALUES\n' + ',\n'.join(prod_inserts) + ';\n')
sql_lines.append('SET IDENTITY_INSERT [Product] OFF;\n')

# 5. PRODUCT VARIANT
sql_lines.append('-- 5. Bảng ProductVariant (Biến thể sản phẩm)')
sql_lines.append('SET IDENTITY_INSERT ProductVariant ON;')

var_inserts = []
seen_skus = set()
variant_id_counter = 1

for r in var_rows[1:]:
    try:
        pid = int(r[1])
    except:
        continue
    
    price = price_map.get(pid, 199000.0)
    
    row_str = ' '.join(r)
    sku_match = re.search(r'([A-Z0-9]{8,15}-[A-Z0-9\-]+)', row_str)
    sku = sku_match.group(1) if sku_match else f"SKU-{pid}-{variant_id_counter}"
    
    if sku in seen_skus:
        sku = f"{sku}-{variant_id_counter}"
    seen_skus.add(sku)
    
    color = 'Đen'
    if 'DO' in sku or 'Đỏ' in row_str: color = 'Đỏ'
    elif 'VANG' in sku or 'Vàng' in row_str: color = 'Vàng'
    elif 'NAU' in sku or 'Nâu' in row_str: color = 'Nâu'
    elif 'DEN' in sku or 'Đen' in row_str: color = 'Đen'
    elif 'HONG' in sku or 'Hồng' in row_str: color = 'Hồng'
    elif 'TRANG' in sku or 'Trắng' in row_str: color = 'Trắng'
    elif 'XANH' in sku or 'Xanh' in row_str: color = 'Xanh'
    
    size = 'M'
    if '-S' in sku or ' S ' in f" {row_str} ": size = 'S'
    elif '-L' in sku or ' L ' in f" {row_str} ": size = 'L'
    elif '-XL' in sku or ' XL ' in f" {row_str} ": size = 'XL'
    elif '-M' in sku: size = 'M'
    
    stock = 10
    if len(r) >= 7 and r[-2].isdigit():
        stock = int(r[-2])
    elif len(r) >= 6 and r[-1].isdigit():
        stock = int(r[-1])
    if stock == 0 or stock > 500: stock = 15
    
    var_inserts.append(f"({variant_id_counter}, {pid}, '{size}', N'{color}', {price:.2f}, {stock}, '{sku}')")
    variant_id_counter += 1

chunk_size = 40
for i in range(0, len(var_inserts), chunk_size):
    chunk = var_inserts[i:i+chunk_size]
    sql_lines.append('INSERT INTO ProductVariant (variant_id, product_id, size, color, price, stock, sku) VALUES\n' + ',\n'.join(chunk) + ';\n')

sql_lines.append('SET IDENTITY_INSERT ProductVariant OFF;\n')

# 6. PRODUCT IMAGE
sql_lines.append('-- 6. Bảng ProductImage (Hình ảnh sản phẩm)')
sql_lines.append('SET IDENTITY_INSERT ProductImage ON;')

img_inserts = []
img_id_counter = 1
seen_img_paths = set()

for r in img_rows[1:]:
    try:
        pid = int(r[1])
    except:
        continue
    
    url = ''
    for cell in r:
        if 'products' in cell:
            url = cell.replace('\\\\', '/').replace('\\', '/')
            break
    if not url:
        url = f"products/{pid}/default.webp"
        
    is_main = 1 if ('-1.webp' in url or len(img_inserts) == 0 or r[0] == '1') else 0
    
    img_inserts.append(f"({img_id_counter}, {pid}, '{url}', {is_main})")
    img_id_counter += 1

for i in range(0, len(img_inserts), chunk_size):
    chunk = img_inserts[i:i+chunk_size]
    sql_lines.append('INSERT INTO ProductImage (image_id, product_id, image_url, is_main) VALUES\n' + ',\n'.join(chunk) + ';\n')

sql_lines.append('SET IDENTITY_INSERT ProductImage OFF;\n')

# 7. CART & ADDRESS
sql_lines.append("""-- 7. Bảng Cart & Address
INSERT INTO Cart ([user_id]) VALUES (2), (3);

INSERT INTO [Address] ([user_id], receiver_name, phone, province, district, ward, detail_address, is_default)
VALUES 
(2, N'Nguyễn Văn A', '0912345678', N'TP. Hồ Chí Minh', N'Quận 1', N'Phường Bến Nghé', N'123 Đường Lê Lợi', 1),
(3, N'Trần Thị B', '0987654321', N'Hà Nội', N'Quận Cầu Giấy', N'Phường Dịch Vọng', N'45 Đường Xuân Thủy', 1);

-- 8. CartItem & Order
INSERT INTO CartItem (cart_id, variant_id, quantity) VALUES (1, 1, 2);

INSERT INTO [Order] ([user_id], address_id, total_amount, shipping_fee, note, [status])
VALUES (2, 1, 298000.00, 30000.00, N'Giao giờ hành chính', 'PENDING');

INSERT INTO OrderDetail (order_id, variant_id, price, quantity, subtotal)
VALUES (1, 1, 149000.00, 2, 298000.00);

INSERT INTO Payment (order_id, method, [status], paid_at)
VALUES (1, 'COD', 'PENDING', NULL);

INSERT INTO Review ([user_id], product_id, rating, comment)
VALUES (2, 1001, 5, N'Sản phẩm rất đẹp, vải mát và vừa vặn!');
""")

with open(r'q:\DOWNLOAD2\ECommerce\src\main\resources\sql\addData.sql', 'w', encoding='utf-8') as f:
    f.write('\n'.join(sql_lines))

print('Successfully generated addData.sql!')
