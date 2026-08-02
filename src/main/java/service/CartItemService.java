package service;

import java.util.List;

import dao.CartItemDao;
import entity.CartItem;

public class CartItemService {
    
    private final CartItemDao cartItemDao = new CartItemDao();

    public List<CartItem> getCartItemsByCartId(long cartId) {
        return cartItemDao.getCartItemsByCartId(cartId);
    }

    public boolean addOrUpdateCartItem(long cartId, long variantId, int quantity) {
        if (quantity <= 0) throw new RuntimeException("Số lượng phải lớn hơn 0");
        return cartItemDao.addOrUpdateCartItem(cartId, variantId, quantity);
    }

    public boolean updateQuantity(long cartItemId, int newQuantity) {
        if (newQuantity <= 0) throw new RuntimeException("Số lượng phải lớn hơn 0");
        return cartItemDao.updateQuantity(cartItemId, newQuantity);
    }

    public boolean deleteCartItem(long cartItemId) {
        return cartItemDao.deleteCartItem(cartItemId);
    }

    public boolean clearCart(long cartId) {
        return cartItemDao.clearCart(cartId);
    }
}
