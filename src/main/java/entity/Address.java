package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Address")

public class Address {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="address_id")
	private long addressId;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	@Column(name="receiver_name", nullable=false, length=100)
	private String receiverName;
	
	@Column(name="phone", nullable=false, length=15)
	private String phone;
	
	@Column(name="province", nullable=false, length=100)
	private String province;
	
	@Column(name="district", nullable=false, length=100)
	private String district;
	
	@Column(name="ward", nullable=false, length=100)
	private String ward;
	
	@Column(name="detail_address", nullable=false, length=255)
	private String detailAddress;
	
	@Column(name="is_default", nullable=false)
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
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	public Address(long addressId, User user, String receiverName, String phone, String province, String district,
			String ward, String detailAddress, boolean isDefault) {
		super();
		this.addressId = addressId;
		this.user = user;
		this.receiverName = receiverName;
		this.phone = phone;
		this.province = province;
		this.district = district;
		this.ward = ward;
		this.detailAddress = detailAddress;
		this.isDefault = isDefault;
	}
	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
