package entity;

import java.time.LocalDateTime;

public class User {
    private long userId;
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String role;
    private boolean status;
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
