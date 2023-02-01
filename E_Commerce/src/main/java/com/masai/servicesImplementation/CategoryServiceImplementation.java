/**
 * 
 */
package com.masai.servicesImplementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.masai.exceptions.ResourceNotFoundException;
import com.masai.model.Category;
import com.masai.modelRequestDto.CategoryRequestDto;
import com.masai.modelRequestDto.CategoryUpdateRequestDto;
import com.masai.modelResponseDto.CategoryResponseDto;
import com.masai.payloads.ApiResponse;
import com.masai.repositories.CategoryRepo;
import com.masai.services.CategoryServices;

/**
 * @author tejas
 *
 */
@Service
public class CategoryServiceImplementation implements CategoryServices {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto) {

		Category category = this.toCategory(categoryRequestDto);

		Category savedCategory = this.categoryRepo.save(category);

		return this.toCategoryResponseDto(savedCategory);
	}

	@Override
	public CategoryResponseDto updateCategory(Integer categoryId, CategoryUpdateRequestDto categoryRequestDto)
			throws ResourceNotFoundException {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		if (categoryRequestDto.getCategoryName() != null) {
			category.setCategoryName(categoryRequestDto.getCategoryName());
		}
		if (categoryRequestDto.getCategoryDescription() != null) {
			category.setCategoryDescription(categoryRequestDto.getCategoryDescription());
		}

		if (categoryRequestDto.getActive() != null) {
			category.setActive(categoryRequestDto.getActive());
		}

		Category savedCategory = this.categoryRepo.save(category);

		return this.toCategoryResponseDto(savedCategory);
	}

	@Override
	public ApiResponse deleteCategoryById(Integer categoryId) throws ResourceNotFoundException {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		this.categoryRepo.delete(category);

		return new ApiResponse(LocalDateTime.now(), "Category Deleted Successfully !", true, category);
	}

	@Override
	public CategoryResponseDto getCategory(Integer categoryId) throws ResourceNotFoundException {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		return this.toCategoryResponseDto(category);
	}

	@Override
	public List<CategoryResponseDto> getAllCategories() {

		List<Category> list = this.categoryRepo.findAll();

		return list.stream().map(this::toCategoryResponseDto).collect(Collectors.toList());
	}

	@Override
	public Page<Category> getAllCategoriesByPage(Integer page, Integer size) {

		Pageable pageable;
		pageable = PageRequest.of(page, size);

		return this.categoryRepo.findAll(pageable);

	}

	@Override
	public Page<Category> getSortedByAnyCategoryDetailsByPage(Integer page, Integer size, String sortBy,
			String sortDirection) {

		Sort sort;
		sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable;
		pageable = PageRequest.of(page, size, sort);

		return this.categoryRepo.findAll(pageable);

	}

	@Override
	public List<CategoryResponseDto> searchCategoriesByKeyword(String keyword) {

		List<Category> list = this.categoryRepo.findByCategoryNameContaining(keyword);

		return list.stream().map(this::toCategoryResponseDto).collect(Collectors.toList());
	}

	private Category toCategory(CategoryRequestDto categoryRequestDto) {

		return this.modelMapper.map(categoryRequestDto, Category.class);

	}

	private CategoryResponseDto toCategoryResponseDto(Category category) {

		return this.modelMapper.map(category, CategoryResponseDto.class);

	}

}
