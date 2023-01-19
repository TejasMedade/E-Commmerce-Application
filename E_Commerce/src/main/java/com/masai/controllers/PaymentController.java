/**
 * 
 */
package com.masai.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("bestbuy/payments")
public class PaymentController {

	@Autowired
	private PaymentServices paymentServices;

	@PostMapping("/")
	public ResponseEntity<PaymentResponseDto> addPaymentMethodHandler(@RequestBody PaymentRequestDto paymentRequestDto) throws DuplicateResourceException {
		
		PaymentResponseDto paymentResponseDto = this.paymentServices.addPaymentMethod(paymentRequestDto);

		return new ResponseEntity<PaymentResponseDto>(paymentResponseDto, HttpStatus.ACCEPTED);
	}

	@GetMapping("/{paymentId}")
	public ResponseEntity<PaymentResponseDto> getPaymentMethodHandler(@PathVariable("paymentId") Integer paymentId)
			throws ResourceNotFoundException {

		PaymentResponseDto paymentResponseDto = this.paymentServices.getPaymentMethod(paymentId);

		return new ResponseEntity<PaymentResponseDto>(paymentResponseDto, HttpStatus.FOUND);

	}
	
	@GetMapping("/")
	public ResponseEntity<List<PaymentResponseDto>> getAllPaymentMethodsHandler()
			throws ResourceNotFoundException {

		List<PaymentResponseDto> list = this.paymentServices.getAllPaymentMethods();

		return new ResponseEntity<List<PaymentResponseDto>>(list, HttpStatus.FOUND);

	}

	@DeleteMapping("/{paymentId}")
	public ResponseEntity<ApiResponse> deletePaymentMethodHandler(@PathVariable("paymentId") Integer paymentId)
			throws ResourceNotFoundException {

		ApiResponse apiResponse = this.paymentServices.deletePaymentMethod(paymentId);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

	@PutMapping("/{paymentId}")
	public ResponseEntity<PaymentResponseDto> revokePaymentMethodHandler(@PathVariable("paymentId") Integer paymentId)
			throws ResourceNotFoundException {

		PaymentResponseDto paymentResponseDto = this.paymentServices.revokePaymentMethod(paymentId);

		return new ResponseEntity<PaymentResponseDto>(paymentResponseDto, HttpStatus.ACCEPTED);
	}

}
