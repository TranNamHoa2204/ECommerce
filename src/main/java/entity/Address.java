package entity;

public class Address {
	private long addressId;
	private User user;
	private String receiverName;
	private String phone;
	private String province;
	private String district;
	private String ward;
	private String detail_address;
	private boolean isDefault;
	public long getAddressId() {
		return addressId;
	}
	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getDistrict () {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getWard() {
		return ward;
	}
	public void setWard(String ward) {
		this.ward = ward;
	}
	public String getDetail_address() {
		return detail_address;
	}
	public void setDetail_address(String detail_address) {
		this.detail_address = detail_address;
	}
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	public Address(long addressId, User user, String receiverName, String phone, String province, String district,
			String ward, String detail_address, boolean isDefault) {
		super();
		this.addressId = addressId;
		this.user = user;
		this.receiverName = receiverName;
		this.phone = phone;
		this.province = province;
		this.district = district;
		this.ward = ward;
		this.detail_address = detail_address;
		this.isDefault = isDefault;
	}
	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
