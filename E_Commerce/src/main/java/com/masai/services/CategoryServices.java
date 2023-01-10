/**
 * 
 */
package com.masai.services;

import java.util.List;

import com.masai.exceptions.ResourceNotFoundException;
import com.masai.modelRequestDto.CategoryRequestDto;
import com.masai.modelResponseDto.CategoryResponseDto;
import com.masai.payloads.ApiResponse;
import com.masai.payloads.PageResponse;

/**
 * @author tejas
 *
 */
public interface CategoryServices {

	List<CategoryResponseDto> getAllCategories();


	CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto);

	CategoryResponseDto updateCategory(Integer categoryId, CategoryRequestDto categoryRequestDto)
			throws ResourceNotFoundException;

	ApiResponse deleteCategoryById(Integer categoryId) throws ResourceNotFoundException;

	CategoryResponseDto getCategory(Integer categoryId) throws ResourceNotFoundException;

	PageResponse getAllCategoriesByPage(Integer page, Integer size);

	PageResponse getSortedByAnyCategoryDetailsByPage(Integer page, Integer size, String sortBy, String sortDirection);

	List<CategoryResponseDto> searchCategoriesByKeyword(String keyword);



}
