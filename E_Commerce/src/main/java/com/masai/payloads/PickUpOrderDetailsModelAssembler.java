/**
 * 
 */
package com.masai.payloads;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.masai.controllers.CustomerController;
import com.masai.controllers.OrderController;
import com.masai.exceptions.DuplicateResourceException;
import com.masai.exceptions.ResourceNotAllowedException;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.model.PickUpOrderRequest;
import com.masai.modelResponseDto.PickUpOrderDetailsResponseDto;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author tejas
 *
 */
@Component
public class PickUpOrderDetailsModelAssembler
		extends RepresentationModelAssemblerSupport<PickUpOrderRequest, PickUpOrderDetailsResponseDto> {

	@Autowired
	private ModelMapper modelMapper;

	public PickUpOrderDetailsModelAssembler() {
		super(OrderController.class, PickUpOrderDetailsResponseDto.class);
	}

	@Override
	public PickUpOrderDetailsResponseDto toModel(PickUpOrderRequest entity) {

		PickUpOrderDetailsResponseDto pickUpOrderDetailsResponseDto = this.modelMapper.map(entity,
				PickUpOrderDetailsResponseDto.class);

		try {

			pickUpOrderDetailsResponseDto.add(linkTo(methodOn(CustomerController.class)
					.getCustomerHandler(pickUpOrderDetailsResponseDto.getCustomer().getContact())).withRel("customer"));

			pickUpOrderDetailsResponseDto.add(linkTo(
					methodOn(OrderController.class).getOrderById(pickUpOrderDetailsResponseDto.getOrder().getOrderId()))
					.withRel("order"));

			pickUpOrderDetailsResponseDto.add(linkTo(methodOn(OrderController.class).revokeOrderPickUpStatus(null,
					pickUpOrderDetailsResponseDto.getOrder().getOrderId())).withRel("revoke pickup status"));

		} catch (ResourceNotFoundException | DuplicateResourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ResourceNotAllowedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return pickUpOrderDetailsResponseDto;
	}

}
