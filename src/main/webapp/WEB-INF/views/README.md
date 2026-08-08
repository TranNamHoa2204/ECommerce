# FashionHub - Trang chủ website bán quần áo (JSP)

## Cấu trúc thư mục
```
webshop/
├── header.jsp        # Header dùng chung (logo, menu, tìm kiếm, tài khoản, giỏ hàng)
├── footer.jsp         # Footer dùng chung (newsletter, thông tin, mạng xã hội)
├── index.jsp           # Trang chủ (banner, danh mục, sản phẩm...)
├── css/
│   └── style.css       # Toàn bộ CSS tuỳ chỉnh
└── images/             # Thư mục để ảnh thật của bạn (hiện dùng ảnh mẫu picsum.photos)
```

## Cách chạy
1. Cài **Apache Tomcat** (9 hoặc 10+) và một IDE hỗ trợ JSP (Eclipse EE, IntelliJ Ultimate, NetBeans...).
2. Tạo một **Dynamic Web Project**, copy toàn bộ nội dung thư mục `webshop/` vào thư mục gốc `WebContent/` (hoặc `webapp/`).
3. Đảm bảo project có thư viện **JSTL** (jakarta.servlet.jsp.jstl / jstl-api + jstl-impl, hoặc jakarta.tags nếu dùng Tomcat 10+). Thêm vào `pom.xml` nếu dùng Maven:
   ```xml
   <dependency>
       <groupId>jakarta.servlet.jsp.jstl</groupId>
       <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
       <version>3.0.0</version>
   </dependency>
   <dependency>
       <groupId>org.glassfish.web</groupId>
       <artifactId>jakarta.servlet.jsp.jstl</artifactId>
       <version>3.0.1</version>
   </dependency>
   ```
   > Lưu ý: file JSP dùng taglib `jakarta.tags.core` (chuẩn Tomcat 10+/Jakarta EE9+).
   > Nếu bạn dùng Tomcat 8/9 (javax.*), đổi URI thành:
   > `<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>`
   > `<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>`
4. Deploy lên Tomcat và truy cập: `http://localhost:8080/webshop/index.jsp`

## Ghi chú quan trọng
- Dữ liệu sản phẩm/danh mục trong `index.jsp` hiện là **dữ liệu mẫu** được tạo bằng scriptlet Java để trang chạy được ngay không cần kết nối Database.
- Trong dự án thực tế, bạn nên:
  - Tạo `Servlet` (ví dụ `HomeController`) truy vấn dữ liệu từ Database (DAO/Service), rồi `request.setAttribute("featuredProducts", list)` và `request.getRequestDispatcher("index.jsp").forward(request, response)`.
  - Xoá phần `<% ... %>` (scriptlet) demo trong `index.jsp`, JSTL `c:forEach` phía dưới sẽ tự động hoạt động với dữ liệu thật vì cùng cấu trúc `Map`/Bean có các thuộc tính `id, name, price, oldPrice, image, colors, badge`.
- `header.jsp` và `footer.jsp` được include vào mọi trang khác bằng:
  ```jsp
  <jsp:include page="header.jsp" />
  ... nội dung trang ...
  <jsp:include page="footer.jsp" />
  ```
- Icon giỏ hàng lấy số lượng từ `session.getAttribute("cartCount")`, icon tài khoản kiểm tra `session.getAttribute("currentUser")` để hiển thị "Đăng nhập" hoặc tên người dùng.
- Toàn bộ ảnh dùng `picsum.photos` làm ảnh minh hoạ — hãy thay bằng ảnh sản phẩm thật trong thư mục `images/`.
