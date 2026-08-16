package entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="[User]")
public class User {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name= "user_id")
	private long userId;

	@Column(name="full_name", nullable=false, length=100)
    private String fullName;

	@Column(name="email", nullable=false, length=100)
    private String email;

	@Column(name="[password]", nullable=false, length=255)
    private String password;

	@Column(name="phone", length=15)
    private String phone;

	@Column(name="[role]", nullable=false, length=20)
    private String role;

	@Column(name="[status]", nullable=false)
    private boolean status;

	@Column(name="created_at")
    private LocalDateTime createdAt;


	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public User(long userId, String fullName, String email, String password, String phone, String role, boolean status,
			LocalDateTime createdAt) {
		super();
		this.userId = userId;
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.role = role;
		this.status = status;
		this.createdAt = createdAt;
	}
	public User() {
		
	}
    
	public User(String fullName, String email, String password, String phone) {
	    this.fullName = fullName;
	    this.email = email;
	    this.password = password;
	    this.phone = phone;
	}
    
}
