/**
 * 
 */
package com.masai.model;

import javax.persistence.Embeddable;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tejas
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CartProductDetails extends RepresentationModel<CartProductDetails> {

	private Integer productId;

	private String productName;

	private String productDescription;

	private Double salePrice;

	private Double marketPrice;

	private Boolean available;

	private Image images;

	private Integer productQuantity;

	private Double productTotalAmount;

}
