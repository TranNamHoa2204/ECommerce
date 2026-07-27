package service;

import java.util.List;

import dao.CartDao;
import entity.Cart;
import entity.CartItem;


public class CartService {
    private CartDao cartDao = new CartDao();

    public Cart getCartByUserId(long userId) {
        Cart cart = cartDao.getCartByUserId(userId);
        if(cart == null){
            throw new RuntimeException("Không tìm thấy giỏ hàng theo id người dùng");
        }
        return cart;
    }

    public List<CartItem> getCartItemsByCartId(long cartId) {
        List<CartItem> cartItems = cartDao.getCartItemsByCartId(cartId);
        return cartItems;
    }

    public boolean addOrUpdateCartItem(long cartId, long variantId, int quantity) {
        return cartDao.addOrUpdateCartItem(cartId, variantId, quantity);
    }

    public boolean deleteCartItem(long cartItemId) {
        return cartDao.deleteCartItem(cartItemId);
    }   

    public Cart createCartForUser(long userId) {
        return cartDao.createCartForUser(userId);
    }
}
