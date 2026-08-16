package service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import entity.Cart;
import entity.CartItem;
import entity.ProductVariant;
import respository.CartItemRepository;
import respository.CartRepository;
import respository.ProductVariantRepository;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductVariantRepository productVariantRepository;

    public CartItemService(CartItemRepository cartItemRepository,
                           CartRepository cartRepository,
                           ProductVariantRepository productVariantRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productVariantRepository = productVariantRepository;
    }

    public List<CartItem> getCartItemsByCartId(long cartId) {
        return cartItemRepository.findByCartCartId(cartId);
    }

    @Transactional
    public boolean addOrUpdateCartItem(long cartId, long variantId, int quantity) {
        if (quantity <= 0) throw new RuntimeException("Số lượng phải lớn hơn 0");

        Optional<CartItem> existingItemOpt = cartItemRepository.findByCartCartIdAndVariantVariantId(cartId, variantId);

        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepository.save(existingItem);
        } else {
            Cart cart = cartRepository.findById(cartId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng"));
            ProductVariant variant = productVariantRepository.findById(variantId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy biến thể sản phẩm"));

            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setVariant(variant);
            newItem.setQuantity(quantity);
            cartItemRepository.save(newItem);
        }
        return true;
    }

    @Transactional
    public boolean updateQuantity(long cartItemId, int newQuantity) {
        if (newQuantity <= 0) throw new RuntimeException("Số lượng phải lớn hơn 0");
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm trong giỏ hàng"));
        cartItem.setQuantity(newQuantity);
        cartItemRepository.save(cartItem);
        return true;
    }

    @Transactional
    public boolean deleteCartItem(long cartItemId) {
        if (!cartItemRepository.existsById(cartItemId)) {
            return false;
        }
        cartItemRepository.deleteById(cartItemId);
        return true;
    }

    @Transactional
    public boolean clearCart(long cartId) {
        cartItemRepository.deleteAllByCartId(cartId);
        return true;
    }
}
