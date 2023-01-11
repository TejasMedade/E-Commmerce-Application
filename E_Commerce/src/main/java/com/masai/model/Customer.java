/**
 * 
 */
package com.masai.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Past;

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
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer customerId;

	private String firstName;

	private String lastName;

	@Column(unique = true)
	private String contact;

	private String email;

	private String password;

	@Past
	private LocalDate dateOfBirth;

	private LocalDate accountCreatedDate;

	@OneToOne(cascade = CascadeType.ALL)
	private Address address;

	@OneToOne(cascade = CascadeType.ALL)
	private Cart cart;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Review> listOfReviews = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Order> listOfOrders = new ArrayList<>();

}
