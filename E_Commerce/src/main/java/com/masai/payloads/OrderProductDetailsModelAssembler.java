/**
 * 
 */
package com.masai.payloads;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.masai.controllers.ProductController;
import com.masai.exceptions.ResourceNotAllowedException;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.model.OrderProductDetails;

/**
 * @author tejas
 *
 */
@Component
public class OrderProductDetailsModelAssembler
		implements RepresentationModelAssembler<OrderProductDetails, EntityModel<OrderProductDetails>> {

	@Autowired
	private ModelMapper modelMapper;

	public EntityModel<OrderProductDetails> toModel(OrderProductDetails entity) {

		OrderProductDetails orderProductDetails = this.modelMapper.map(entity, OrderProductDetails.class);

		EntityModel<OrderProductDetails> model = EntityModel.of(orderProductDetails);

		// Self Link
		try {
			model.add(linkTo(methodOn(ProductController.class).getProductByIdHandler(model.getContent().getProductId()))
					.withSelfRel());
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ResourceNotAllowedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return model;

	}

}
