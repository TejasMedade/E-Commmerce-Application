/**
 * 
 */
package com.masai.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cartId;

	private Integer cartQuantity;

	private Double cartTotalAmount;
	
	@ElementCollection
	private List<CartProductDetails> listOfProducts = new ArrayList<>();

}
