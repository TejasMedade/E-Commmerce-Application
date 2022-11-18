/**
 * 
 */
package com.masai.services;

import java.util.List;

import com.masai.dto.ProductDTO;
import com.masai.exceptions.CartException;
import com.masai.exceptions.CustomerException;
import com.masai.exceptions.LoginException;
import com.masai.exceptions.ProductException;
import com.masai.model.Cart;

/**
 * @author tejas
 *
 */

public interface CartService {

	public String deleteallproducts(String key)
			throws ProductException, CartException, LoginException, CustomerException;

	public List<ProductDTO> viewallproducts(String key)
			throws LoginException, CustomerException, ProductException, CartException;

	public Cart addproduct(Integer productId, Integer quantity, String key)
			throws ProductException, LoginException, CustomerException, CartException;

	public Cart deleteproduct(Integer productId, String key)
			throws LoginException, CustomerException, CartException, ProductException;

	public Cart udpateproductquantity(String key, Integer productId, Integer quantity)
			throws CartException, LoginException, CustomerException, ProductException;

}
