/**
 * 
 */
package com.masai.services;

import com.masai.exceptions.ResourceNotFoundException;
import com.masai.modelRequestDto.AddressRequestDto;
import com.masai.modelRequestDto.AddressUpdateRequestDto;
import com.masai.modelResponseDto.AddressResponseDto;
import com.masai.modelResponseDto.CustomerResponseDto;

/**
 * @author tejas
 *
 */
public interface AddressServices {

	AddressResponseDto getAddressDetails(String customerContact) throws ResourceNotFoundException;


	CustomerResponseDto addAddressDetails(String customerContact, AddressRequestDto addressRequestDto)
			throws ResourceNotFoundException;

	CustomerResponseDto udpateAddressDetails(String customerContact, AddressUpdateRequestDto addressUpdateRequestDto)
			throws ResourceNotFoundException;

}
