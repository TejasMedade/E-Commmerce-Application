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

import com.masai.controllers.CartController;
import com.masai.controllers.CategoryController;
import com.masai.controllers.OrderController;
import com.masai.controllers.ProductController;
import com.masai.exceptions.ResourceNotAllowedException;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.model.Product;
import com.masai.modelResponseDto.ProductResponseDto;

/**
 * @author tejas
 *
 */
@Component
public class ProductModelAssembler extends RepresentationModelAssemblerSupport<Product, ProductResponseDto> {

	@Autowired
	private ModelMapper modelMapper;

	public ProductModelAssembler() {
		super(ProductController.class, ProductResponseDto.class);
	}

	@Override
	public ProductResponseDto toModel(Product entity) {

		ProductResponseDto productResponseDto = this.modelMapper.map(entity, ProductResponseDto.class);

		try {
			// Self Link
			productResponseDto.add(
					linkTo(methodOn(ProductController.class).getProductByIdHandler(productResponseDto.getProductId()))
							.withSelfRel());
			
			// Category Link
			productResponseDto.add(linkTo(
					methodOn(CategoryController.class).getCategoryHandler(productResponseDto.getCategory().getCategoryId()))
					.withRel("category"));
			
			// AddtoCart
			productResponseDto.add(linkTo(
					methodOn(CartController.class).addProducttoCartHandler(null, productResponseDto.getProductId(), 1))
					.withRel("add to cart"));

			// BuyProduct
			productResponseDto.add(linkTo(
					methodOn(OrderController.class).orderProduct(null, null, productResponseDto.getProductId(), 1))
					.withRel("buy product"));

		} catch (ResourceNotFoundException | ResourceNotAllowedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return productResponseDto;
	}

}
