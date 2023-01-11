/**
 * 
 */
package com.masai.controllers;

import java.util.List;

import javax.validation.Valid;

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

import com.masai.exceptions.ResourceNotFoundException;
import com.masai.modelRequestDto.CustomerRequestDto;
import com.masai.modelRequestDto.CustomerUpdateRequestDto;
import com.masai.modelResponseDto.CustomerDetailsResponseDto;
import com.masai.modelResponseDto.CustomerResponseDto;
import com.masai.payloads.ApiResponse;
import com.masai.services.CustomerServices;

/**
 * @author tejas
 *
 */

@RestController
@RequestMapping("bestbuy/customers")
public class CustomerController {

	@Autowired
	private CustomerServices customerService;

	@PostMapping("/")
	public ResponseEntity<CustomerDetailsResponseDto> signUpCustomerHandler(
			@Valid @RequestBody CustomerRequestDto customerRequestDto) {

		CustomerDetailsResponseDto signUpCustomer = this.customerService.signUpCustomer(customerRequestDto);

		return new ResponseEntity<CustomerDetailsResponseDto>(signUpCustomer, HttpStatus.ACCEPTED);
	}

	@PutMapping("/update/{contact}")
	public ResponseEntity<CustomerDetailsResponseDto> updateCustomerDetailsHandler(
			@PathVariable("contact") String contact,
			@Valid @RequestBody CustomerUpdateRequestDto customerUpdateRequestDto) throws ResourceNotFoundException {

		CustomerDetailsResponseDto updateCustomerDetails = this.customerService.updateCustomerDetails(contact,
				customerUpdateRequestDto);

		return new ResponseEntity<CustomerDetailsResponseDto>(updateCustomerDetails, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/delete/{contact}")
	public ResponseEntity<ApiResponse> deleteCustomerAccountHandler(@PathVariable("contact") String contact)
			throws ResourceNotFoundException {

		ApiResponse deleteCustomerAccount = this.customerService.deleteCustomerAccount(contact);

		return new ResponseEntity<ApiResponse>(deleteCustomerAccount, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<CustomerResponseDto>> getAllCustomerDetailsHandler() {

		List<CustomerResponseDto> allCustomerDetails = this.customerService.getAllCustomerDetails();

		return new ResponseEntity<List<CustomerResponseDto>>(allCustomerDetails, HttpStatus.FOUND);
	}

	@GetMapping("/{contact}")
	public ResponseEntity<CustomerResponseDto> getCustomerHandler(@PathVariable("contact") String contact)
			throws ResourceNotFoundException {

		CustomerResponseDto customer = this.customerService.getCustomer(contact);

		return new ResponseEntity<CustomerResponseDto>(customer, HttpStatus.FOUND);
	}
}
