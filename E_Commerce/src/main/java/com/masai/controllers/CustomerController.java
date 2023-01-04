/**
 * 
 */
package com.masai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exceptions.ResourceNotFoundException;
import com.masai.modelRequestDto.CustomerDetailsRequestDto;
import com.masai.modelRequestDto.CustomerRequestDto;
import com.masai.modelResponseDto.CustomerDetailsResponseDto;
import com.masai.modelResponseDto.CustomerResponseDto;
import com.masai.payloads.ApiResponse;
import com.masai.services.CustomerService;

/**
 * @author tejas
 *
 */

@RestController
@RequestMapping("bestbuy/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping("/")
	public ResponseEntity<CustomerResponseDto> signUpCustomerHandler(
			@RequestBody CustomerRequestDto customerRequestDto) {

		CustomerResponseDto signUpCustomer = this.customerService.signUpCustomer(customerRequestDto);

		return new ResponseEntity<CustomerResponseDto>(signUpCustomer, HttpStatus.ACCEPTED);
	}

	@PostMapping("/update/{contact}")
	public ResponseEntity<CustomerDetailsResponseDto> updateCustomerDetailsHandler(
			@PathVariable("contact") String contact, @RequestBody CustomerDetailsRequestDto customerDetailsRequestDto)
			throws ResourceNotFoundException {

		CustomerDetailsResponseDto updateCustomerDetails = this.customerService.updateCustomerDetails(contact,
				customerDetailsRequestDto);

		return new ResponseEntity<CustomerDetailsResponseDto>(updateCustomerDetails, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/delete/{contact}")
	public ResponseEntity<ApiResponse> deleteCustomerAccountHandler(@PathVariable("contact") String contact)
			throws ResourceNotFoundException {

		ApiResponse deleteCustomerAccount = this.customerService.deleteCustomerAccount(contact);

		return new ResponseEntity<ApiResponse>(deleteCustomerAccount, HttpStatus.OK);
	}
}
