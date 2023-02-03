/**
 * 
 */
package com.masai.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Past;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tejas
 *
 */

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;

	private String firstName;

	private String lastName;

	@Column(unique = true)
	private String contact;

	private String email;

	private String password;

	@Embedded
	private Image image;

	@Past
	private LocalDate dateOfBirth;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime accountCreatedDate;

	@UpdateTimestamp
	@Column(nullable = false)
	private LocalDateTime accountUpdatedDate;

	@OneToOne(cascade = CascadeType.ALL)
	private Address address;

	@OneToOne(cascade = CascadeType.ALL)
	private Cart cart;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Review> listOfReviews = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL)
	private List<Order> listOfOrders = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL)
	private List<CancelOrderRequest> listOfCancelOrders = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL)
	private List<ReplaceOrderRequest> listOfReplaceOrders = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL)
	private List<RefundOrderRequest> listOfRefunds = new ArrayList<>();
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user", referencedColumnName = "userId"), inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "roleId"))
	private Set<Role> roles = new HashSet<>();

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return this.roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
		
	}

	@Override
	public String getUsername() {
		return this.contact;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
