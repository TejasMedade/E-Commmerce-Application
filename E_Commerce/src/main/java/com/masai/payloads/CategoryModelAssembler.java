/**
 * 
 */
package com.masai.payloads;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.masai.controllers.CategoryController;
import com.masai.controllers.ProductController;
import com.masai.exceptions.ResourceNotAllowedException;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.model.Category;
import com.masai.modelResponseDto.CategoryResponseDto;

/**
 * @author tejas
 *
 */
@Component
public class CategoryModelAssembler extends RepresentationModelAssemblerSupport<Category, CategoryResponseDto> {

	@Autowired
	private ModelMapper modelMapper;

	public CategoryModelAssembler() {

		super(CategoryController.class, CategoryResponseDto.class);

	}

	@Override
	public CategoryResponseDto toModel(Category entity) {

		CategoryResponseDto categoryResponseDto = this.modelMapper.map(entity, CategoryResponseDto.class);

		try {
			categoryResponseDto.add(
					linkTo(methodOn(CategoryController.class).getCategoryHandler(categoryResponseDto.getCategoryId()))
							.withSelfRel());
			

			// Product by Category Link
			categoryResponseDto.add(linkTo(methodOn(ProductController.class).searchByCategoryHandler(categoryResponseDto.getCategoryId()))
					.withRel("products by category "));


		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		} catch (ResourceNotAllowedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return categoryResponseDto;
	}

}
