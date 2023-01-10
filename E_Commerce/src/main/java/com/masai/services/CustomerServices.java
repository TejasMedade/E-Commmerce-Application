/**
 * 
 */
package com.masai.services;

import java.util.List;

import com.masai.exceptions.ResourceNotFoundException;
import com.masai.modelRequestDto.CustomerRequestDto;
import com.masai.modelRequestDto.CustomerUpdateRequestDto;
import com.masai.modelResponseDto.CustomerDetailsResponseDto;
import com.masai.modelResponseDto.CustomerResponseDto;
import com.masai.payloads.ApiResponse;

/**
 * @author tejas
 *
 */

public interface CustomerServices {

	public CustomerDetailsResponseDto signUpCustomer(CustomerRequestDto customerRequestDto);

	public CustomerDetailsResponseDto updateCustomerDetails(String contact,
			CustomerUpdateRequestDto customerUpdateRequestDto) throws ResourceNotFoundException;

	public ApiResponse deleteCustomerAccount(String contact) throws ResourceNotFoundException;

	public List<CustomerResponseDto> getAllCustomerDetails();

	public CustomerResponseDto getCustomer(String contact) throws ResourceNotFoundException;

}
