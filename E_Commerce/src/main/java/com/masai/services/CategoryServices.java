/**
 * 
 */
package com.masai.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.masai.exceptions.ResourceNotFoundException;
import com.masai.model.Category;
import com.masai.modelRequestDto.CategoryRequestDto;
import com.masai.modelRequestDto.CategoryUpdateRequestDto;
import com.masai.modelResponseDto.CategoryResponseDto;
import com.masai.payloads.ApiResponse;

/**
 * @author tejas
 *
 */
public interface CategoryServices {

	List<CategoryResponseDto> getAllCategories();


	CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto);


	ApiResponse deleteCategoryById(Integer categoryId) throws ResourceNotFoundException;

	CategoryResponseDto getCategory(Integer categoryId) throws ResourceNotFoundException;

	Page<Category> getAllCategoriesByPage(Integer page, Integer size);

	Page<Category> getSortedByAnyCategoryDetailsByPage(Integer page, Integer size, String sortBy, String sortDirection);

	List<CategoryResponseDto> searchCategoriesByKeyword(String keyword);


	CategoryResponseDto updateCategory(Integer categoryId, CategoryUpdateRequestDto categoryRequestDto)
			throws ResourceNotFoundException;



}
