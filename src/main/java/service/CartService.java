package service;

import dao.CartDao;
import entity.Cart;


public class CartService {
    private final CartDao cartDao = new CartDao();

    public Cart getCartByUserId(long userId) {
        Cart cart = cartDao.getCartByUserId(userId);
        if(cart == null){
            throw new RuntimeException("Không tìm thấy giỏ hàng theo id người dùng");
        }
        return cart;
    }

    public Cart createCartForUser(long userId) {
        return cartDao.createCartForUser(userId);
    }
}
