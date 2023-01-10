/**
 * 
 */
package com.masai.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exceptions.ResourceNotFoundException;
import com.masai.modelRequestDto.ProductRequestDto;
import com.masai.modelResponseDto.ProductResponseDto;
import com.masai.payloads.ApiResponse;
import com.masai.payloads.AppConstants;
import com.masai.payloads.PageResponse;
import com.masai.services.ProductServices;

/**
 * @author tejas
 *
 */
@RestController
@RequestMapping("bestbuy/products")
public class ProductController {

	@Autowired
	private ProductServices productServices;

	@GetMapping("/all")
	public ResponseEntity<List<ProductResponseDto>> getAllProductsHandler() {

		List<ProductResponseDto> allProducts = this.productServices.getAllProducts();

		return new ResponseEntity<List<ProductResponseDto>>(allProducts, HttpStatus.OK);
	}

	@PostMapping("/{categoryId}")
	public ResponseEntity<ProductResponseDto> addProductHandler(@PathVariable("categoryId") Integer categoryId,
			@Valid @RequestBody ProductRequestDto productRequestDto) throws ResourceNotFoundException {

		ProductResponseDto product = this.productServices.addProduct(categoryId, productRequestDto);

		return new ResponseEntity<ProductResponseDto>(product, HttpStatus.ACCEPTED);
	}

	@PutMapping("/{productId}")
	public ResponseEntity<ProductResponseDto> updateProductDetailsHandler(@PathVariable("productId") Integer productId,
			@Valid @RequestBody ProductRequestDto productRequestDto) throws ResourceNotFoundException {

		ProductResponseDto updatedProduct = this.productServices.updateProductDetails(productId, productRequestDto);

		return new ResponseEntity<ProductResponseDto>(updatedProduct, HttpStatus.ACCEPTED);

	}

	@DeleteMapping("/{productId}")
	public ResponseEntity<ApiResponse> deleteProductByIdHandler(@PathVariable("productId") Integer productId)
			throws ResourceNotFoundException {

		ApiResponse apiResponse = this.productServices.deleteProductById(productId);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

	@GetMapping("/{productId}")
	public ResponseEntity<ProductResponseDto> getProductByIdHandler(@PathVariable("productId") Integer productId)
			throws ResourceNotFoundException {

		ProductResponseDto productResponseDto = this.productServices.getProductById(productId);

		return new ResponseEntity<ProductResponseDto>(productResponseDto, HttpStatus.FOUND);
	}

	@GetMapping("/")
	public ResponseEntity<PageResponse> getProductsByPageHandler(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize) {

		PageResponse productsByPage = this.productServices.getProductsByPage(pageNumber, pageSize);

		return new ResponseEntity<PageResponse>(productsByPage, HttpStatus.OK);
	}

	@GetMapping("/sort/rating")
	public ResponseEntity<PageResponse> getSortedProductsByRatingHandler(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.SORTDIRECTION, required = false) String sortDirection) {

		PageResponse productsByRating = this.productServices.getSortedProductsByRating(pageNumber, pageSize,
				sortDirection);

		return new ResponseEntity<PageResponse>(productsByRating, HttpStatus.OK);
	}

	@GetMapping("/sort/price")
	public ResponseEntity<PageResponse> getSortedProductsBySalePriceHandler(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.SORTDIRECTION, required = false) String sortDirection) {

		PageResponse productsBySalePrice = this.productServices.getSortedProductsBySalePrice(pageNumber, pageSize,
				sortDirection);

		return new ResponseEntity<PageResponse>(productsBySalePrice, HttpStatus.OK);
	}

	@GetMapping("/sort/manufacturingyear")
	public ResponseEntity<PageResponse> getSortedProductsByManufacturingYearHandler(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.SORTDIRECTION, required = false) String sortDirection) {

		PageResponse productsByManufacturingYear = this.productServices.getSortedProductsByManufacturingYear(pageNumber,
				pageSize, sortDirection);

		return new ResponseEntity<PageResponse>(productsByManufacturingYear, HttpStatus.OK);

	}

	@GetMapping("/sortby")
	public ResponseEntity<PageResponse> getSortedByAnyProductDetailsByPageHandler(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.SORTBY, required = false) String sortBy,
			@RequestParam(defaultValue = AppConstants.SORTDIRECTION, required = false) String sortDirection) {

		PageResponse productsByAnyProductDetailsByPage = this.productServices
				.getSortedByAnyProductDetailsByPage(pageNumber, pageSize, sortBy, sortDirection);

		return new ResponseEntity<PageResponse>(productsByAnyProductDetailsByPage, HttpStatus.OK);

	}

	@GetMapping("/sort/dateadded")
	public ResponseEntity<PageResponse> getSortedProductsByAddedDateHandler(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.SORTDIRECTION, required = false) String sortDirection) {

		PageResponse productsByManufacturingYear = this.productServices.getSortedProductsByAddedDate(pageNumber,
				pageSize, sortDirection);

		return new ResponseEntity<PageResponse>(productsByManufacturingYear, HttpStatus.OK);

	}

	@GetMapping("/search")
	public ResponseEntity<List<ProductResponseDto>> searchProductsByProductNameKeywordHandler(
			@RequestParam String keyword) {

		List<ProductResponseDto> productsByProductNameKeyword = this.productServices
				.searchProductsByProductNameKeyword(keyword);

		return new ResponseEntity<List<ProductResponseDto>>(productsByProductNameKeyword, HttpStatus.FOUND);
	}

	@PutMapping("/{productId}/categories/{categoryId}")
	public ResponseEntity<ProductResponseDto> updateProductCategoryHandler(Integer productId, Integer categoryId)
			throws ResourceNotFoundException {

		ProductResponseDto updatedProduct = this.productServices.updateProductCategory(productId, categoryId);

		return new ResponseEntity<ProductResponseDto>(updatedProduct, HttpStatus.ACCEPTED);
	}

	@GetMapping("/{categoryId}")
	public ResponseEntity<List<ProductResponseDto>> searchByCategoryHandler(
			@PathVariable("categoryId") Integer categoryId) throws ResourceNotFoundException {

		List<ProductResponseDto> searchByCategory = this.productServices.searchByCategory(categoryId);

		return new ResponseEntity<List<ProductResponseDto>>(searchByCategory, HttpStatus.FOUND);
	}

}
