package controller;

import java.io.IOException;
import java.util.List;

import entity.Product;
import entity.ProductVariant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ProductService;
import service.ProductVariantService;

@WebServlet("/product")
public class ProductServlet extends HttpServlet{
    private final ProductService productService = new ProductService();
    private final ProductVariantService productVariantService = new ProductVariantService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null) action = "";

        switch(action){ 
            // action nên là động từ
            case "listProducts" -> showListProducts(req, resp);
            case "detail" -> showProductDetail(req, resp);
            case "search" -> searchProductByName(req, resp);
            case "filterCategory" -> filterByCategory(req, resp);
            case "filterBrand" -> filterByBrand(req, resp);
            case "filterVariant" -> filterByVariant(req,resp);
            default -> showListProducts(req, resp);
        }
        
    }

    private void showListProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        List<Product> list = productService.getAllProducts();

        req.setAttribute("listProducts", list);
        req.getRequestDispatcher("/WEB-INF/views/listProducts.jsp").forward(req, resp); 
    }

    private void showProductDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long id =  Long.parseLong(req.getParameter("productId"));
            Product product = productService.getProductById(id);
            req.setAttribute("detail", product);
            req.getRequestDispatcher("/WEB-INF/views/productDetail.jsp").forward(req, resp);
        } catch(RuntimeException e){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void searchProductByName(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        try {
            String keyWord = req.getParameter("name");
            List<Product> list = productService.searchProductsByName(keyWord);
            req.setAttribute("search", keyWord);
            req.setAttribute("listProducts", list);
            req.getRequestDispatcher("/WEB-INF/views/listProducts.jsp").forward(req,resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST); 
        }
        
    }

    private void filterByCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        try {
            long id = Long.parseLong(req.getParameter("categoryId"));
            List<Product> list = productService.getProductsByCategoryId(id);
            req.setAttribute("listProducts", list);
            req.getRequestDispatcher("/WEB-INF/views/listProducts.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } 
    }

    private void filterByBrand(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        try {
            long id = Long.parseLong(req.getParameter("brandId"));
            List<Product> list = productService.getProductsByBrandId(id);
            req.setAttribute("listProducts", list);
            req.getRequestDispatcher("/WEB-INF/views/listProducts.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        
    }

    private void filterByVariant(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        try {
            long id = Long.parseLong(req.getParameter("productId"));
            String size = req.getParameter("size");
            String color = req.getParameter("color");

            List<ProductVariant> list = productVariantService.findVariant(id, size, color);
            req.setAttribute("listVariants", list);
            req.getRequestDispatcher("/WEB-INF/views/listProducts.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
