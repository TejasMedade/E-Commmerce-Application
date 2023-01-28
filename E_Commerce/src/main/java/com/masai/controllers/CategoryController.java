/**
 * 
 */
package com.masai.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
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

import com.masai.exceptions.ResourceNotAllowedException;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.model.Category;
import com.masai.modelRequestDto.CategoryRequestDto;
import com.masai.modelRequestDto.CategoryUpdateRequestDto;
import com.masai.modelResponseDto.CategoryResponseDto;
import com.masai.payloads.ApiResponse;
import com.masai.payloads.AppConstants;
import com.masai.payloads.CategoryModelAssembler;
import com.masai.services.CategoryServices;

/**
 * @author tejas
 *
 */
@RestController
@RequestMapping("/bestbuy/categories")
public class CategoryController {

	@Autowired
	private CategoryServices categoryServices;

	@Autowired
	private CategoryModelAssembler categoryModelAssembler;

	@Autowired
	private PagedResourcesAssembler<Category> pagedResourcesAssembler;

	@GetMapping("/all")
	public ResponseEntity<List<CategoryResponseDto>> getAllCategoriesHandler() {

		List<CategoryResponseDto> allCategories = this.categoryServices.getAllCategories();

		return new ResponseEntity<List<CategoryResponseDto>>(allCategories, HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<CategoryResponseDto> addCategoryHandler(
			@Valid @RequestBody CategoryRequestDto categoryRequestDto) throws ResourceNotFoundException, ResourceNotAllowedException {

		CategoryResponseDto categoryResponseDto = this.categoryServices.addCategory(categoryRequestDto);

		// Self Link
		categoryResponseDto
				.add(linkTo(methodOn(CategoryController.class).getCategoryHandler(categoryResponseDto.getCategoryId()))
						.withSelfRel());

		// Collection Link
		categoryResponseDto.add(linkTo(methodOn(CategoryController.class).getAllCategoriesByPageHandler(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CategoryResponseDto>(categoryResponseDto, HttpStatus.CREATED);
	}

	@PutMapping("/{categoryId}/update")
	public ResponseEntity<CategoryResponseDto> updateCategoryHandler(@PathVariable("categoryId") Integer categoryId,
			@Valid @RequestBody CategoryUpdateRequestDto categoryRequestDto)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		CategoryResponseDto categoryResponseDto = this.categoryServices.updateCategory(categoryId, categoryRequestDto);

		// Self Link
		categoryResponseDto
				.add(linkTo(methodOn(CategoryController.class).getCategoryHandler(categoryResponseDto.getCategoryId()))
						.withSelfRel());

		// Product by Category Link
		categoryResponseDto.add(linkTo(methodOn(ProductController.class).searchByCategoryHandler(categoryId))
				.withRel("products by category "));

		// Collection Link
		categoryResponseDto.add(linkTo(methodOn(CategoryController.class).getAllCategoriesByPageHandler(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CategoryResponseDto>(categoryResponseDto, HttpStatus.OK);
	}

	@DeleteMapping("/{categoryId}/delete")
	public ResponseEntity<ApiResponse> deleteCategoryByIdHandler(@PathVariable("categoryId") Integer categoryId)
			throws ResourceNotFoundException {

		ApiResponse apiResponse = this.categoryServices.deleteCategoryById(categoryId);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryResponseDto> getCategoryHandler(@PathVariable("categoryId") Integer categoryId)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		CategoryResponseDto categoryResponseDto = this.categoryServices.getCategory(categoryId);

		// Self Link
		categoryResponseDto
				.add(linkTo(methodOn(CategoryController.class).getCategoryHandler(categoryResponseDto.getCategoryId()))
						.withSelfRel());

		// Product by Category Link
		categoryResponseDto.add(linkTo(methodOn(ProductController.class).searchByCategoryHandler(categoryId))
				.withRel("products by category "));

		// Collection Link
		categoryResponseDto.add(linkTo(methodOn(CategoryController.class).getAllCategoriesByPageHandler(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CategoryResponseDto>(categoryResponseDto, HttpStatus.OK);
	}

	@GetMapping("/all/page")
	public ResponseEntity<CollectionModel<CategoryResponseDto>> getAllCategoriesByPageHandler(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize) {

		Page<Category> pageResponse = this.categoryServices.getAllCategoriesByPage(pageNumber, pageSize);

		PagedModel<CategoryResponseDto> model = this.pagedResourcesAssembler.toModel(pageResponse,
				categoryModelAssembler);

		// Collection Link
		model.add(linkTo(methodOn(CategoryController.class).getAllCategoriesByPageHandler(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CollectionModel<CategoryResponseDto>>(model, HttpStatus.OK);
	}

	@GetMapping("/all/sortby")
	public ResponseEntity<CollectionModel<CategoryResponseDto>> getSortedByAnyCategoryDetailsByPageHandler(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.CATEGORYSORTBY, required = false) String sortBy,
			@RequestParam(defaultValue = AppConstants.SORTDIRECTION, required = false) String sortDirection) {

		Page<Category> pageResponse = this.categoryServices.getSortedByAnyCategoryDetailsByPage(pageNumber, pageSize,
				sortBy, sortDirection);

		PagedModel<CategoryResponseDto> model = this.pagedResourcesAssembler.toModel(pageResponse,
				categoryModelAssembler);

		// Collection Link
		model.add(linkTo(methodOn(CategoryController.class).getAllCategoriesByPageHandler(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CollectionModel<CategoryResponseDto>>(model, HttpStatus.OK);

	}

	@GetMapping("/all/search/{keyword}")
	public ResponseEntity<List<CategoryResponseDto>> searchCategoriesByKeywordHandler(
			@PathVariable("keyword") String keyword) {

		List<CategoryResponseDto> categoriesByKeyword = this.categoryServices.searchCategoriesByKeyword(keyword);

		return new ResponseEntity<List<CategoryResponseDto>>(categoriesByKeyword, HttpStatus.OK);
	}

}
