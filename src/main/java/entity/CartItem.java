package entity;

public class CartItem {
	private long cartItemId;
	private Cart cart;
	private ProductVariant variant;
	private int quantity;
	public long getCartItemId() {
		return cartItemId;
	}
	public void setCartItemId(long cartItemId) {
		this.cartItemId = cartItemId;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public ProductVariant getVariant() {
		return variant;
	}
	public void setVariant(ProductVariant variant) {
		this.variant = variant;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public CartItem(long cartItemId, Cart cart, ProductVariant variant, int quantity) {
		super();
		this.cartItemId = cartItemId;
		this.cart = cart;
		this.variant = variant;
		this.quantity = quantity;
	}
	public CartItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
