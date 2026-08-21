package controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import security.CustomUserDetails;
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

    // Lấy giỏ hàng của user đang đăng nhập
    @GetMapping
    public ResponseEntity<List<CartItemResponseDTO>> getMyCartItems(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        long userId = userDetails.getUser().getUserId();
        Cart cart = cartService.getCartByUserId(userId);
        List<CartItem> items = cartItemService.getCartItemsByCartId(cart.getCartId());
        List<CartItemResponseDTO> list = items.stream()
                .map(CartItemResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(list);
    }

    // Khởi tạo giỏ hàng cho user đang đăng nhập (gọi khi đăng ký xong)
    @PostMapping("/init")
    public ResponseEntity<Map<String, Long>> initCart(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        long userId = userDetails.getUser().getUserId();
        Cart cart = cartService.createCartForUser(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("cartId", cart.getCartId()));
    }

    // Thêm hoặc cộng dồn sản phẩm vào giỏ
    @PostMapping("/items")
    public ResponseEntity<Map<String, String>> addOrUpdateCartItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody CartItemRequestDTO request) {
        long userId = userDetails.getUser().getUserId();
        Cart cart = cartService.getCartByUserId(userId);
        cartItemService.addOrUpdateCartItem(cart.getCartId(), request.getVariantId(), request.getQuantity());
        return ResponseEntity.ok(Map.of("message", "Đã cập nhật sản phẩm trong giỏ hàng thành công"));
    }

    // Cập nhật số lượng một item
    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<Map<String, String>> updateQuantity(
            @PathVariable("cartItemId") long cartItemId,
            @RequestParam("quantity") int quantity) {
        cartItemService.updateQuantity(cartItemId, quantity);
        return ResponseEntity.ok(Map.of("message", "Đã cập nhật số lượng thành công"));
    }

    // Xóa một item khỏi giỏ
    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable("cartItemId") long cartItemId) {
        cartItemService.deleteCartItem(cartItemId);
        return ResponseEntity.noContent().build();
    }

    // Xóa toàn bộ giỏ hàng
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        long userId = userDetails.getUser().getUserId();
        Cart cart = cartService.getCartByUserId(userId);
        cartItemService.clearCart(cart.getCartId());
        return ResponseEntity.noContent().build();
    }
}
