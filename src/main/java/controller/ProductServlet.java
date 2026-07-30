package controller;

import java.io.IOException;
import java.util.List;

import entity.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ProductService;

@WebServlet("/user")
public class ProductServlet extends HttpServlet{
    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null) action = "";

        switch(action){
            case "productList" -> productList(req, resp);
            case "productInfo" -> productInfo(req, resp);
            case "productSearch" -> productSearch(req, resp);
            
        }
        
    }

    private void productList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        List<Product> list = productService.getAllProducts();

        req.setAttribute("productList", list);
        req.getRequestDispatcher("/WEB-INF/views/productList.jsp").forward(req, resp); 
        
    }

    private void productInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product product = productService.getProductById(Long.parseLong(req.getParameter("productId")));
        req.setAttribute("productInfo", product);
        req.getRequestDispatcher("/WEB-INF/views/productInfo").forward(req, resp);
    }

    private void productSearch(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        String keyWord = req.getParameter("keyWord");
        List<Product> list = productService.searchProductsByName(keyWord);
        
        req.setAttribute("keyWord", keyWord);
        req.setAttribute("productList", list);
        req.getRequestDispatcher("/WEB-INF/views/productList.jsp").forward(req,resp);
    }
}
