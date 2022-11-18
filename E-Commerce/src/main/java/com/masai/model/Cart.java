/**
 * 
 */
package com.masai.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.masai.dto.ProductDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tejas
 *
 */

@Entity
@Data
@NoArgsConstructor
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer cartId;

	@OneToOne(cascade = CascadeType.ALL)
	private Customer customer;

	@ElementCollection
	private List<ProductDTO> products = new ArrayList<>();

}
