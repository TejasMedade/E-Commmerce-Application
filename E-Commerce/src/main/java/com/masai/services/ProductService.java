/**
 * 
 */
package com.masai.services;

import java.util.List;

import com.masai.exceptions.AdminException;
import com.masai.exceptions.CustomerException;
import com.masai.exceptions.LoginException;
import com.masai.exceptions.ProductException;
import com.masai.model.Product;

/**
 * @author tejas
 *
 */

public interface ProductService {

	public List<Product> viewallProducts() throws ProductException;

	// Admin Role
	public Product insertProduct(String key, Product product) throws LoginException, AdminException;

	// Admin Role
	public Product updateProduct(String key, Product product)
			throws LoginException, ProductException, AdminException;

	public Product viewProduct(Integer productId) throws ProductException;

	public List<Product> viewProductByCategory(String category_Name) throws ProductException;

	// Admin Role
	public String removeProduct(String key, Integer product_Id) throws LoginException, ProductException, AdminException;

}
