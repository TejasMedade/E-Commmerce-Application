/**
 * 
 */
package com.masai.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tejas
 *
 */

@Entity
@Data
@NoArgsConstructor
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer customerId;

	private String firstName;

	private String lastName;

	private String mobileNumber;

	private String password;

	@OneToOne(cascade = CascadeType.ALL)
	private Address address;

	private String email;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Order> listOfOrders = new ArrayList<>();

	/**
	 * @return the listOfOrders
	 */
	@JsonIgnore
	public List<Order> getListOfOrders() {
		return listOfOrders;
	}

	/**
	 * @param listOfOrders the listOfOrders to set
	 */
	@JsonIgnore
	public void setListOfOrders(List<Order> listOfOrders) {
		this.listOfOrders = listOfOrders;
	}
	
	

}
