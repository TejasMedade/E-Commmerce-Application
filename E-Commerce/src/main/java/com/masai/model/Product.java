/**
 * 
 */
package com.masai.model;


import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tejas
 *
 */

@Entity
@Data
@NoArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer productId;

	private String productName;

	private Double price;

	private String colour;

	private String dimension;

	private String specification;

	private String manufacturer;

	@Embedded
	private Category category;

	private Integer quantity;

}
