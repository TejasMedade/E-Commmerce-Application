/**
 * 
 */
package com.masai.modelResponseDto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.masai.model.CartProductDetails;

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
public class CartResponseDto extends RepresentationModel<CartResponseDto>{
	
	private Integer cartId;

	private Integer cartQuantity;

	private Double cartTotalAmount;

	private List<CartProductDetails> listOfProducts = new ArrayList<>();
	
}
