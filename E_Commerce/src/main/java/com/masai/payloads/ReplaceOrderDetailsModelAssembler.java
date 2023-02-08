/**
 * 
 */
package com.masai.payloads;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author tejas
 *
 */

/**
 * 
 */

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.masai.controllers.CustomerController;
import com.masai.controllers.OrderController;
import com.masai.exceptions.ResourceNotAllowedException;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.model.ReplaceOrderRequest;
import com.masai.modelResponseDto.ReplaceOrderDetailsResponseDto;

/**
 * @author tejas
 *
 */
@Component
public class ReplaceOrderDetailsModelAssembler
		extends RepresentationModelAssemblerSupport<ReplaceOrderRequest, ReplaceOrderDetailsResponseDto> {

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ReplaceOrderDetailsResponseDto toModel(ReplaceOrderRequest entity) {

		ReplaceOrderDetailsResponseDto refundOrderDetailsResponseDto = this.modelMapper.map(entity,
				ReplaceOrderDetailsResponseDto.class);

		try {

			refundOrderDetailsResponseDto.add(linkTo(methodOn(CustomerController.class)
					.getCustomerHandler(refundOrderDetailsResponseDto.getCustomer().getContact())).withRel("customer"));

			refundOrderDetailsResponseDto.add(linkTo(
					methodOn(OrderController.class).getOrderById(refundOrderDetailsResponseDto.getOrder().getOrderId()))
					.withRel("order"));

			refundOrderDetailsResponseDto.add(linkTo(methodOn(OrderController.class)
					.getOrderById(refundOrderDetailsResponseDto.getReplacemenetOrderId()))
					.withRel("replacement order"));

			refundOrderDetailsResponseDto.add(linkTo(methodOn(OrderController.class).approveReplacementRequest(null,
					refundOrderDetailsResponseDto.getReplaceOrderRequestId())).withRel("approve replacement"));

		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ResourceNotAllowedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return refundOrderDetailsResponseDto;
	}

	public ReplaceOrderDetailsModelAssembler() {

		super(OrderController.class, ReplaceOrderDetailsResponseDto.class);
	}
}
