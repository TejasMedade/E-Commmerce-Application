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
import com.masai.modelResponseDto.CategoryResponseDto;
import com.masai.payloads.ApiResponse;
import com.masai.payloads.PageResponse;
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
	public CategoryResponseDto updateCategory(Integer categoryId, CategoryRequestDto categoryRequestDto)
			throws ResourceNotFoundException {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		if (categoryRequestDto.getCategoryName() != null) {
			category.setCategoryName(categoryRequestDto.getCategoryName());
		}
		if (categoryRequestDto.getCategoryDescription() != null) {
		}
		if (categoryRequestDto.getSubCategory() != null) {
			category.setSubCategory(categoryRequestDto.getSubCategory());
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

		return new ApiResponse(LocalDateTime.now(), "Category Deleted Successfully !", true);
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

		List<CategoryResponseDto> listOfCategories = list.stream().map(c -> this.toCategoryResponseDto(c))
				.collect(Collectors.toList());

		return listOfCategories;
	}

	@Override
	public PageResponse getAllCategoriesByPage(Integer page, Integer size) {

		Pageable pageable;
		pageable = PageRequest.of(page, size);

		Page<Category> categoryPage = this.categoryRepo.findAll(pageable);

		List<Category> content = categoryPage.getContent();

		List<CategoryResponseDto> listOfCategories = content.stream().map(this::toCategoryResponseDto)
				.collect(Collectors.toList());

		PageResponse pageResponse = new PageResponse();

		pageResponse.setContent(listOfCategories);
		pageResponse.setLastPage(categoryPage.isLast());
		pageResponse.setPageNumber(categoryPage.getNumber());
		pageResponse.setPageSize(categoryPage.getSize());
		pageResponse.setTotalPages(categoryPage.getTotalPages());
		pageResponse.setTotalElements(categoryPage.getTotalElements());

		return pageResponse;
	}

	@Override
	public PageResponse getSortedByAnyCategoryDetailsByPage(Integer page, Integer size, String sortBy,
			String sortDirection) {

		Sort sort;
		sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable;
		pageable = PageRequest.of(page, size, sort);

		Page<Category> categoryPage = this.categoryRepo.findAll(pageable);

		List<Category> content = categoryPage.getContent();

		List<CategoryResponseDto> listOfCategories = content.stream().map(this::toCategoryResponseDto)
				.collect(Collectors.toList());

		PageResponse pageResponse = new PageResponse();

		pageResponse.setContent(listOfCategories);
		pageResponse.setLastPage(categoryPage.isLast());
		pageResponse.setPageNumber(categoryPage.getNumber());
		pageResponse.setPageSize(categoryPage.getSize());
		pageResponse.setTotalPages(categoryPage.getTotalPages());
		pageResponse.setTotalElements(categoryPage.getTotalElements());

		return pageResponse;

	}

	@Override
	public List<CategoryResponseDto> searchCategoriesByKeyword(String keyword) {

		List<Category> list = this.categoryRepo.findByCategoryNameContaining(keyword);

		return list.stream().map(this::toCategoryResponseDto)
				.collect(Collectors.toList());
	}

	private Category toCategory(CategoryRequestDto categoryRequestDto) {
		
		return this.modelMapper.map(categoryRequestDto, Category.class);
	
	}

	private CategoryResponseDto toCategoryResponseDto(Category category) {
	
		return this.modelMapper.map(category, CategoryResponseDto.class);
	
	}

}
