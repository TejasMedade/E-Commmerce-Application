/**
 * 
 */
package com.masai.payloads;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.masai.controllers.CustomerController;
import com.masai.controllers.OrderController;
import com.masai.controllers.PaymentController;
import com.masai.controllers.ProductController;
import com.masai.exceptions.ResourceNotAllowedException;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.model.Order;
import com.masai.model.OrderProductDetails;
import com.masai.modelResponseDto.OrderDetailsResponseDto;

/**
 * @author tejas
 *
 */
@Component
public class OrderDetailsModelAssembler extends RepresentationModelAssemblerSupport<Order, OrderDetailsResponseDto> {

	@Autowired
	private ModelMapper modelMapper;

	public OrderDetailsModelAssembler() {
		super(OrderController.class, OrderDetailsResponseDto.class);
	}

	@Override
	public OrderDetailsResponseDto toModel(Order entity) {

		OrderDetailsResponseDto orderDetailsResponseDto = this.modelMapper.map(entity, OrderDetailsResponseDto.class);

		List<OrderProductDetails> listOfProducts = orderDetailsResponseDto.getListOfProducts();

		for (OrderProductDetails o : listOfProducts) {

			// Self Product Link
			try {
				o.add(linkTo(methodOn(ProductController.class).getProductByIdHandler(o.getProductId())).withSelfRel());
			} catch (ResourceNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ResourceNotAllowedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		try {
			// Self Order
			orderDetailsResponseDto
					.add(linkTo(methodOn(OrderController.class).getOrderById(orderDetailsResponseDto.getOrderId()))
							.withSelfRel());

			// Customer Link
			orderDetailsResponseDto.add(
					linkTo(methodOn(CustomerController.class).getCustomerHandler(entity.getCustomer().getContact()))
							.withRel("customer"));

			// Payment Link
			orderDetailsResponseDto.add(linkTo(
					methodOn(PaymentController.class).getPaymentMethodHandler(entity.getPayment().getPaymentId()))
					.withRel("payment"));

		
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		} catch (ResourceNotAllowedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return orderDetailsResponseDto;
	}

}
