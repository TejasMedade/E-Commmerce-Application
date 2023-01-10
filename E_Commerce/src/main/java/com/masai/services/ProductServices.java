/**
 * 
 */
package com.masai.services;

import java.util.List;

import com.masai.exceptions.ResourceNotFoundException;
import com.masai.modelRequestDto.ProductRequestDto;
import com.masai.modelResponseDto.ProductResponseDto;
import com.masai.payloads.ApiResponse;
import com.masai.payloads.PageResponse;

/**
 * @author tejas
 *
 */
public interface ProductServices {

	List<ProductResponseDto> getAllProducts();

	ProductResponseDto addProduct(Integer categoryId, ProductRequestDto productRequestDto)
			throws ResourceNotFoundException;

	ProductResponseDto updateProductDetails(Integer productId, ProductRequestDto productRequestDto)
			throws ResourceNotFoundException;

	ApiResponse deleteProductById(Integer productId) throws ResourceNotFoundException;

	ProductResponseDto getProductById(Integer productId) throws ResourceNotFoundException;

	PageResponse getProductsByPage(Integer page, Integer size);

	PageResponse getSortedProductsByRating(Integer page, Integer size, String sortDirection);

	PageResponse getSortedProductsBySalePrice(Integer page, Integer size, String sortDirection);

	PageResponse getSortedProductsByManufacturingYear(Integer page, Integer size, String sortDirection);

	PageResponse getSortedByAnyProductDetailsByPage(Integer page, Integer size, String sortDirection, String sortBy);

	PageResponse getSortedProductsByAddedDate(Integer page, Integer size, String sortDirection);

	List<ProductResponseDto> searchProductsByProductNameKeyword(String keyword);

	ProductResponseDto updateProductCategory(Integer productId, Integer categoryId) throws ResourceNotFoundException;

	List<ProductResponseDto> searchByCategory(Integer categoryId) throws ResourceNotFoundException;

}
