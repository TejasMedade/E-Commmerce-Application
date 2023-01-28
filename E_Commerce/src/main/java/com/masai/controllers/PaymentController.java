/**
 * 
 */
package com.masai.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.masai.exceptions.DuplicateResourceException;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.modelRequestDto.PaymentRequestDto;
import com.masai.modelResponseDto.PaymentResponseDto;
import com.masai.payloads.ApiResponse;
import com.masai.services.PaymentServices;

/**
 * @author tejas
 *
 */

@RestController
@RequestMapping("/bestbuy/payments")
public class PaymentController {

	@Autowired
	private PaymentServices paymentServices;

	@PostMapping("/")
	public ResponseEntity<PaymentResponseDto> addPaymentMethodHandler(@RequestBody PaymentRequestDto paymentRequestDto)
			throws DuplicateResourceException, ResourceNotFoundException {

		PaymentResponseDto paymentResponseDto = this.paymentServices.addPaymentMethod(paymentRequestDto);

		// Self Links
		paymentResponseDto.add(
				linkTo(methodOn(PaymentController.class).getPaymentMethodHandler(paymentResponseDto.getPaymentId()))
						.withSelfRel());

		// Collection Links
		paymentResponseDto.add(linkTo(methodOn(PaymentController.class).getAllPaymentMethodsHandler())
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<PaymentResponseDto>(paymentResponseDto, HttpStatus.CREATED);
	}

	@GetMapping("/{paymentId}")
	public ResponseEntity<PaymentResponseDto> getPaymentMethodHandler(@PathVariable("paymentId") Integer paymentId)
			throws ResourceNotFoundException {

		PaymentResponseDto paymentResponseDto = this.paymentServices.getPaymentMethod(paymentId);

		// Self Links
		paymentResponseDto.add(
				linkTo(methodOn(PaymentController.class).getPaymentMethodHandler(paymentResponseDto.getPaymentId()))
						.withSelfRel());

		// Collection Links
		paymentResponseDto.add(linkTo(methodOn(PaymentController.class).getAllPaymentMethodsHandler())
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<PaymentResponseDto>(paymentResponseDto, HttpStatus.OK);

	}

	@GetMapping("/all")
	public ResponseEntity<CollectionModel<PaymentResponseDto>> getAllPaymentMethodsHandler()
			throws ResourceNotFoundException {

		List<PaymentResponseDto> list = this.paymentServices.getAllPaymentMethods();

		for (PaymentResponseDto paymentResponseDto : list) {

			// Self Links
			paymentResponseDto.add(
					linkTo(methodOn(PaymentController.class).getPaymentMethodHandler(paymentResponseDto.getPaymentId()))
							.withSelfRel());

		}

		CollectionModel<PaymentResponseDto> model = CollectionModel.of(list);

		// Collection Links
		model.add(linkTo(methodOn(PaymentController.class).getAllPaymentMethodsHandler())
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CollectionModel<PaymentResponseDto>>(model, HttpStatus.OK);

	}

	@DeleteMapping("/{paymentId}/delete")
	public ResponseEntity<ApiResponse> deletePaymentMethodHandler(@PathVariable("paymentId") Integer paymentId)
			throws ResourceNotFoundException {

		ApiResponse apiResponse = this.paymentServices.deletePaymentMethod(paymentId);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.GONE);
	}

	@PutMapping("/{paymentId}/revoke")
	public ResponseEntity<PaymentResponseDto> revokePaymentMethodHandler(@PathVariable("paymentId") Integer paymentId)
			throws ResourceNotFoundException {

		PaymentResponseDto paymentResponseDto = this.paymentServices.revokePaymentMethod(paymentId);

		// Self Links
		paymentResponseDto.add(
				linkTo(methodOn(PaymentController.class).getPaymentMethodHandler(paymentResponseDto.getPaymentId()))
						.withSelfRel());

		// Collection Links
		paymentResponseDto.add(linkTo(methodOn(PaymentController.class).getAllPaymentMethodsHandler())
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<PaymentResponseDto>(paymentResponseDto, HttpStatus.OK);
	}

}
