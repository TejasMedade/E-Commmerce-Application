package com.masai.services;

import java.util.List;

import com.masai.exceptions.DuplicateResourceException;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.modelRequestDto.PaymentRequestDto;
import com.masai.modelResponseDto.PaymentResponseDto;
import com.masai.payloads.ApiResponse;

/**
 * @author tejas
 *
 */

public interface PaymentServices {
	
	
	PaymentResponseDto addPaymentMethod(PaymentRequestDto paymentRequestDto) throws DuplicateResourceException;

	PaymentResponseDto getPaymentMethod(Integer paymentId) throws ResourceNotFoundException;

	ApiResponse deletePaymentMethod(Integer paymentId) throws ResourceNotFoundException;

	PaymentResponseDto revokePaymentMethod(Integer paymentId) throws ResourceNotFoundException;

	List<PaymentResponseDto> getAllPaymentMethods() throws ResourceNotFoundException;

}
