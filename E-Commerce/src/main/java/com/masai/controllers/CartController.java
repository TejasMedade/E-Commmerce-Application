/**
* 
*/
package com.masai.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.dto.ProductDTO;
import com.masai.exceptions.CartException;
import com.masai.exceptions.CustomerException;
import com.masai.exceptions.LoginException;
import com.masai.exceptions.ProductException;
import com.masai.model.Cart;
import com.masai.services.CartService;

/**
 * @author tejas
 *
 */

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@PutMapping("/addProducts")
	public ResponseEntity<Cart> addProductToCartHandler(@RequestParam Integer productId, @RequestParam String key,
			@RequestParam Integer quantity) throws ProductException, LoginException, CustomerException, CartException {

		Cart updatedCart = cartService.addproduct(productId, quantity, key);

		return new ResponseEntity<Cart>(updatedCart, HttpStatus.ACCEPTED);

	}

	@DeleteMapping("/deleteCart")
	public ResponseEntity<String> emptyCartHandler(@RequestParam String key)
			throws ProductException, CartException, LoginException, CustomerException {

		String result = cartService.deleteallproducts(key);

		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	@GetMapping("/allProducts")
	public ResponseEntity<List<ProductDTO>> viewAllCartProductsHandler(@RequestParam String key)
			throws LoginException, CustomerException, ProductException, CartException {

		List<ProductDTO> listOfProducts = cartService.viewallproducts(key);

		return new ResponseEntity<List<ProductDTO>>(listOfProducts, HttpStatus.OK);

	}

	@DeleteMapping("/deleteProduct")
	public ResponseEntity<Cart> deleteProductFromCartHandler(@RequestParam Integer productId, @RequestParam String key)
			throws LoginException, CustomerException, CartException, ProductException {

		Cart cart = cartService.deleteproduct(productId, key);

		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}

	@PutMapping("/updateProductQuantity")
	public ResponseEntity<Cart> udpateProductQuantityHandler(@RequestParam String key, @RequestParam Integer productId,
			@RequestParam Integer quantity) throws CartException, LoginException, CustomerException, ProductException {

		Cart cart = cartService.udpateproductquantity(key, productId, quantity);

		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}

}
