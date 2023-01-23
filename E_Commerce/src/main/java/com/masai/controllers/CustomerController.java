/**
 * 
 */
package com.masai.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.masai.exceptions.FileTypeNotValidException;
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

	@PutMapping("/{contact}/image")
	public ResponseEntity<CustomerDetailsResponseDto> updateCustomerImage(String contact, MultipartFile image)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException {

		CustomerDetailsResponseDto updateCustomerImage = this.customerService.updateCustomerImage(contact, image);

		return new ResponseEntity<CustomerDetailsResponseDto>(updateCustomerImage, HttpStatus.ACCEPTED);
	}

	@PutMapping("/{contact}")
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

	@GetMapping("/{firstname}")
	public ResponseEntity<List<CustomerResponseDto>> searchByfirstName(@PathVariable("firstname") String firstName) {

		List<CustomerResponseDto> searchByfirstName = this.customerService.searchByfirstName(firstName);

		return new ResponseEntity<List<CustomerResponseDto>>(searchByfirstName, HttpStatus.FOUND);

	}

	@GetMapping("/{lastname}")
	public ResponseEntity<List<CustomerResponseDto>> searchBylastName(@PathVariable("lastname") String lastName) {

		List<CustomerResponseDto> searchBylastName = this.customerService.searchBylastName(lastName);

		return new ResponseEntity<List<CustomerResponseDto>>(searchBylastName, HttpStatus.FOUND);
	}

	@GetMapping("/{email}")
	public ResponseEntity<List<CustomerResponseDto>> searchByemailId(@PathVariable("email") String email) {

		List<CustomerResponseDto> searchByemailId = this.customerService.searchByemailId(email);

		return new ResponseEntity<List<CustomerResponseDto>>(searchByemailId, HttpStatus.FOUND);

	}

	// method to serve images
	@GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void serveImageHandler(@PathVariable("imageName") String imageName, HttpServletResponse response)
			throws IOException, ResourceNotFoundException, FileTypeNotValidException {

		this.customerService.serveCustomerImage(imageName, response);

	}

	// method to delete images
	@DeleteMapping("/images/{fileName}")
	public ResponseEntity<ApiResponse> deleteImage(@PathVariable("fileName") String fileName) throws IOException {

		ApiResponse apiResponse = this.customerService.deleteCustomerImage(fileName);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}
}
