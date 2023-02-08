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
import com.masai.controllers.PaymentController;
import com.masai.exceptions.ResourceNotAllowedException;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.model.RefundOrderRequest;
import com.masai.modelResponseDto.RefundOrderDetailsResponseDto;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author tejas
 *
 */
@Component
public class RefundOrderDetailsModelAssembler
		extends RepresentationModelAssemblerSupport<RefundOrderRequest, RefundOrderDetailsResponseDto> {

	@Autowired
	private ModelMapper modelMapper;

	public RefundOrderDetailsModelAssembler() {

		super(OrderController.class, RefundOrderDetailsResponseDto.class);

	}

	@Override
	public RefundOrderDetailsResponseDto toModel(RefundOrderRequest entity) {

		RefundOrderDetailsResponseDto refundOrderDetailsResponseDto = this.modelMapper.map(entity,
				RefundOrderDetailsResponseDto.class);

		try {

			refundOrderDetailsResponseDto.add(linkTo(methodOn(CustomerController.class)
					.getCustomerHandler(refundOrderDetailsResponseDto.getCustomer().getContact())).withRel("customer"));

			refundOrderDetailsResponseDto.add(linkTo(
					methodOn(OrderController.class).getOrderById(refundOrderDetailsResponseDto.getOrder().getOrderId()))
					.withRel("order"));

			refundOrderDetailsResponseDto.add(linkTo(methodOn(PaymentController.class)
					.getPaymentMethodHandler(refundOrderDetailsResponseDto.getPayment().getPaymentId()))
					.withRel("payment"));

			refundOrderDetailsResponseDto.add(linkTo(methodOn(OrderController.class).approveRefundsById(null,
					refundOrderDetailsResponseDto.getRefundOrderRequestId())).withRel("Approve Refund"));

		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ResourceNotAllowedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return refundOrderDetailsResponseDto;
	}

}
