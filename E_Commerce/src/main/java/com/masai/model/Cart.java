/**
 * 
 */
package com.masai.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ASUS
 *
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

	private Integer cartId;

	private Integer cartQuantity;

	private Double cartTotalAmount;

	private List<Product> listOfProducts = new ArrayList<>();

	@OneToOne
	private Customer customer;

}
