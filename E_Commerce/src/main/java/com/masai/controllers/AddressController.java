/**
 * 
 */
package com.masai.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.modelRequestDto.AddressRequestDto;
import com.masai.modelRequestDto.AddressUpdateRequestDto;
import com.masai.modelResponseDto.AddressResponseDto;
import com.masai.modelResponseDto.CustomerResponseDto;
import com.masai.services.AddressServices;

/**
 * @author tejas
 *
 */
@RestController
@RequestMapping("bestbuy/addresses")
public class AddressController {

	@Autowired
	private AddressServices addressServices;

	@GetMapping("/{contact}")
	public ResponseEntity<AddressResponseDto> getAddressDetailsHandler(@PathVariable("contact") String customerContact)
			throws ResourceNotFoundException {

		AddressResponseDto addressDetails = this.addressServices.getAddressDetails(customerContact);

		return new ResponseEntity<AddressResponseDto>(addressDetails, HttpStatus.OK);
	}

	@PostMapping("/{contact}")
	public ResponseEntity<AddressResponseDto> addAddressDetailsHandler(@PathVariable("contact") String customerContact,
			@Valid @RequestBody AddressRequestDto addressRequestDto) throws ResourceNotFoundException {

		AddressResponseDto addressDetails = this.addressServices.getAddressDetails(customerContact);

		return new ResponseEntity<AddressResponseDto>(addressDetails, HttpStatus.OK);
	}

	@PutMapping("/{contact}")
	public ResponseEntity<CustomerResponseDto> udpateAddressDetails(@PathVariable("contact") String customerContact,
			@RequestBody AddressUpdateRequestDto addressUpdateRequestDto) throws ResourceNotFoundException {

		CustomerResponseDto udpatedAddressDetails = this.addressServices.udpateAddressDetails(customerContact,
				addressUpdateRequestDto);

		return new ResponseEntity<CustomerResponseDto>(udpatedAddressDetails, HttpStatus.OK);

	}

}
