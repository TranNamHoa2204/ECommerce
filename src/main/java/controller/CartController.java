package controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import DTO.request.CartItemRequestDTO;
import DTO.response.CartItemResponseDTO;
import entity.Cart;
import entity.CartItem;
import jakarta.validation.Valid;
import service.CartItemService;
import service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final CartItemService cartItemService;

    public CartController(CartService cartService, CartItemService cartItemService) {
        this.cartService = cartService;
        this.cartItemService = cartItemService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartItemResponseDTO>> getCartItemsByUserId(@PathVariable("userId") long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        List<CartItem> items = cartItemService.getCartItemsByCartId(cart.getCartId());
        List<CartItemResponseDTO> list = items.stream()
                .map(CartItemResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<Map<String, Long>> createCartForUser(@PathVariable("userId") long userId) {
        Cart cart = cartService.createCartForUser(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("cartId", cart.getCartId()));
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<Map<String, String>> addOrUpdateCartItem(
            @PathVariable("cartId") long cartId,
            @Valid @RequestBody CartItemRequestDTO request) {
        
        cartItemService.addOrUpdateCartItem(cartId, request.getVariantId(), request.getQuantity());
        return ResponseEntity.ok(Map.of("message", "Đã cập nhật sản phẩm trong giỏ hàng thành công"));
    }

    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<Map<String, String>> updateQuantity(
            @PathVariable("cartItemId") long cartItemId,
            @RequestParam("quantity") int quantity) {

        cartItemService.updateQuantity(cartItemId, quantity);
        return ResponseEntity.ok(Map.of("message", "Đã cập nhật số lượng thành công"));
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable("cartItemId") long cartItemId) {
        cartItemService.deleteCartItem(cartItemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable("cartId") long cartId) {
        cartItemService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }
}
