package com.spring.security.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import com.spring.security.customAnotation.UniqueEmail;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames="email"))
public class User{
	
	public static enum Role{
		UNVERIFIED, ADMIN, BLOCKED
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long user_id;
	private String fName;
	private String lName;
	@UniqueEmail(message="Email Already Exist")
	private String email;
	@Column(nullable=false)
	@Size(min=6,max=32)
	private String password;
	private Boolean enabled;
	@ElementCollection(fetch=FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	protected Set<Role> roles = new HashSet<Role>();

	public User() {}
	public User(User user) {
		this.user_id = user.getUser_id();
		this.fName = user.getfName();
		this.lName = user.getlName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.roles = user.getRoles();
		this.enabled = user.getEnabled();
	}
	
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
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
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", fName=" + fName + ", lName=" + lName + ", email=" + email + ", pass="
				+ password + ", enabled=" + enabled + ", roles=" + roles + "]";
	}

}
