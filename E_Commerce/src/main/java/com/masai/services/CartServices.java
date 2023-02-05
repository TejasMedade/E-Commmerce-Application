/**
 * 
 */
package com.masai.services;


import com.masai.exceptions.ResourceNotAllowedException;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.modelResponseDto.CartResponseDto;
import com.masai.modelResponseDto.OrderResponseDto;
import com.masai.payloads.ApiResponse;

/**
 * @author tejas
 *
 */

public interface CartServices {
	

	ApiResponse addProducttoCart(String contact, Integer productId, Integer productQuantity)
			throws ResourceNotFoundException, ResourceNotAllowedException;

	ApiResponse updateCartProductQuantity(String contact, Integer productId, Integer quantity) throws ResourceNotFoundException, ResourceNotAllowedException;

	CartResponseDto emptyCart(String contact) throws ResourceNotFoundException;

	CartResponseDto deleteProductfromCart(String contact, Integer productId) throws ResourceNotFoundException;
	
	CartResponseDto getCart(String contact) throws ResourceNotFoundException;


	OrderResponseDto buyCart(String contact, Integer paymentId) throws ResourceNotFoundException, ResourceNotAllowedException;
	

}
