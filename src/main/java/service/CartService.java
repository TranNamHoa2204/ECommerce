package service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import entity.Cart;
import entity.User;
import respository.CartRepository;
import respository.UserRepository;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    public Cart getCartByUserId(long userId) {
        return cartRepository.findByUserUserId(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng theo id người dùng"));
    }

    @Transactional
    public Cart createCartForUser(long userId) {
        return cartRepository.findByUserUserId(userId).orElseGet(() -> {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setCreatedAt(LocalDateTime.now());
            return cartRepository.save(cart);
        });
    }
}
