/**
 * 
 */
package com.masai.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exceptions.AdminException;
import com.masai.exceptions.CustomerException;
import com.masai.exceptions.LoginException;
import com.masai.exceptions.ProductException;
import com.masai.model.Product;
import com.masai.services.ProductService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

/**
 * @author tejas
 *
 */
@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/viewProducts")
	public ResponseEntity<List<Product>> viewAllProductsHandler() throws ProductException {

		List<Product> listofproducts = productService.viewallProducts();

		return new ResponseEntity<List<Product>>(listofproducts, HttpStatus.OK);
	}

	// Admin Role
	@PostMapping("/addProduct")
	public ResponseEntity<Product> addProductHandler(@RequestParam String key, @RequestBody Product product)
			throws CustomerException, LoginException, AdminException {

		Product added_product = productService.insertProduct(key, product);

		return new ResponseEntity<Product>(added_product, HttpStatus.ACCEPTED);
	}

	// Admin Role
	@PutMapping("/updateProduct")
	public ResponseEntity<Product> updateProductHandler(@RequestParam String key, @RequestBody Product product)
			throws LoginException, ProductException, AdminException {

		Product updated_product = productService.updateProduct(key, product);

		return new ResponseEntity<Product>(updated_product, HttpStatus.ACCEPTED);
	}

	@GetMapping("/viewProduct")
	public ResponseEntity<Product> viewProductHandler(@RequestParam Integer productId) throws ProductException {

		Product product = productService.viewProduct(productId);

		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@GetMapping("/viewProductByCategory")
	public ResponseEntity<List<Product>> viewProductByCategoryHandler(@RequestParam String category_Name)
			throws ProductException {

		List<Product> listofproducts = productService.viewProductByCategory(category_Name);

		return new ResponseEntity<List<Product>>(listofproducts, HttpStatus.OK);

	}

	// Admin Role
	@GetMapping("/deleteProduct")
	public ResponseEntity<String> removeProductHandler(@RequestParam String key, @RequestParam Integer product_Id)
			throws CustomerException, LoginException, ProductException, AdminException {

		String result = productService.removeProduct(key, product_Id);

		return new ResponseEntity<String>(result, HttpStatus.OK);

	}

}
