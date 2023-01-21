/**
 * 
 */
package com.masai.controllers;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.masai.exceptions.FileTypeNotValidException;
import com.masai.exceptions.ResourceNotAllowedException;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.modelRequestDto.ProductRequestDto;
import com.masai.modelRequestDto.ProductUpdateRequestDto;
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

	@Autowired
	private ObjectMapper objectMapper;

	@GetMapping("/all")
	public ResponseEntity<List<ProductResponseDto>> getAllProductsHandler() {

		List<ProductResponseDto> allProducts = this.productServices.getAllProducts();

		return new ResponseEntity<List<ProductResponseDto>>(allProducts, HttpStatus.OK);
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
			@RequestParam(defaultValue = AppConstants.RATINGSORTDIRECTION, required = false) String sortDirection) {

		PageResponse productsByRating = this.productServices.getSortedProductsByRating(pageNumber, pageSize,
				sortDirection);

		return new ResponseEntity<PageResponse>(productsByRating, HttpStatus.OK);
	}

	@GetMapping("/filter/category/{categoryId}/sort/rating")
	public ResponseEntity<PageResponse> findByCategorySortByRating(@PathVariable("categoryId") Integer categoryId,
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.RATINGSORTDIRECTION, required = false) String sortDirection)
			throws ResourceNotFoundException {

		PageResponse pageResponse = this.productServices.findByCategorySortByRating(categoryId, pageNumber, pageSize,
				sortDirection);

		return new ResponseEntity<PageResponse>(pageResponse, HttpStatus.OK);
	}

	@GetMapping("/filter/category/{categoryId}/sort/addeddate")
	public ResponseEntity<PageResponse> findByCategorySortByProductAddedDate(
			@PathVariable("categoryId") Integer categoryId,
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.RATINGSORTDIRECTION, required = false) String sortDirection)
			throws ResourceNotFoundException {

		PageResponse pageResponse = this.productServices.findByCategorySortByProductAddedDate(categoryId, pageNumber,
				pageSize, sortDirection);

		return new ResponseEntity<PageResponse>(pageResponse, HttpStatus.OK);
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

	@GetMapping("/sort/manufacturingmonth")
	public ResponseEntity<PageResponse> getSortedProductsByManufacturingMonthHandler(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.SORTDIRECTION, required = false) String sortDirection) {

		PageResponse productsByManufacturingYear = this.productServices
				.getSortedProductsByManufacturingMonth(pageNumber, pageSize, sortDirection);

		return new ResponseEntity<PageResponse>(productsByManufacturingYear, HttpStatus.OK);

	}

	@GetMapping("/sortby")
	public ResponseEntity<PageResponse> getSortedByAnyProductDetailsByPageHandler(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.PRODUCTSORTBY, required = false) String sortBy,
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

	@GetMapping("/categories/{categoryId}")
	public ResponseEntity<List<ProductResponseDto>> searchByCategoryHandler(
			@PathVariable("categoryId") Integer categoryId) throws ResourceNotFoundException {

		List<ProductResponseDto> searchByCategory = this.productServices.searchByCategory(categoryId);

		return new ResponseEntity<List<ProductResponseDto>>(searchByCategory, HttpStatus.FOUND);
	}

	@PostMapping("/{categoryId}")
	public ResponseEntity<ProductResponseDto> addProductHandler(@PathVariable("categoryId") Integer categoryId,
			@Valid @RequestParam String productRequestDto, @RequestParam MultipartFile[] images)
			throws ResourceNotFoundException, ResourceNotAllowedException, IOException, FileTypeNotValidException {

		// converting String into JSON
		ProductRequestDto productrequestDto = objectMapper.readValue(productRequestDto, ProductRequestDto.class);

		ProductResponseDto product = this.productServices.addProduct(categoryId, productrequestDto, images);

		return new ResponseEntity<ProductResponseDto>(product, HttpStatus.ACCEPTED);
	}

	@PutMapping("/{productId}/categories/{categoryId}")
	public ResponseEntity<ProductResponseDto> updateProductCategoryHandler(@PathVariable("productId") Integer productId,
			@PathVariable("categoryId") Integer categoryId)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		ProductResponseDto updatedProduct = this.productServices.updateProductCategory(productId, categoryId);

		return new ResponseEntity<ProductResponseDto>(updatedProduct, HttpStatus.ACCEPTED);
	}

	@PutMapping("/{productId}/onsale")
	ResponseEntity<ProductResponseDto> revokeProductOnDiscountSale(@PathVariable("productId") Integer productId)
			throws ResourceNotFoundException {

		ProductResponseDto productResponseDto = this.productServices.revokeProductOnDiscountSale(productId);

		return new ResponseEntity<ProductResponseDto>(productResponseDto, HttpStatus.OK);
	}

	@PutMapping("/{productId}")
	public ResponseEntity<ProductResponseDto> updateProductDetailsHandler(@PathVariable("productId") Integer productId,
			@Valid @RequestBody ProductUpdateRequestDto productRequestDto) throws ResourceNotFoundException {

		ProductResponseDto updatedProduct = this.productServices.updateProductDetails(productId, productRequestDto);

		return new ResponseEntity<ProductResponseDto>(updatedProduct, HttpStatus.ACCEPTED);

	}

	@PutMapping("/{productId}/available")
	public ResponseEntity<ProductResponseDto> revokeAvailability(@PathVariable("productId") Integer productId)
			throws ResourceNotFoundException {

		ProductResponseDto productResponseDto = this.productServices.revokeAvailability(productId);

		return new ResponseEntity<ProductResponseDto>(productResponseDto, HttpStatus.OK);
	}

	@PutMapping("/{productId}/buyerschoice")
	public ResponseEntity<ProductResponseDto> revokeBuyersChoice(@PathVariable("productId") Integer productId)
			throws ResourceNotFoundException {

		ProductResponseDto productResponseDto = this.productServices.revokeBuyersChoice(productId);

		return new ResponseEntity<ProductResponseDto>(productResponseDto, HttpStatus.OK);
	}

	@DeleteMapping("/{productId}")
	public ResponseEntity<ApiResponse> deleteProductByIdHandler(@PathVariable("productId") Integer productId)
			throws ResourceNotFoundException {

		ApiResponse apiResponse = this.productServices.deleteProductById(productId);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

}
