/**
 * 
 */
package com.masai.servicesImplementation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exceptions.ResourceNotFoundException;
import com.masai.model.Customer;
import com.masai.modelRequestDto.CustomerRequestDto;
import com.masai.modelRequestDto.CustomerUpdateRequestDto;
import com.masai.modelResponseDto.CustomerDetailsResponseDto;
import com.masai.modelResponseDto.CustomerResponseDto;
import com.masai.payloads.ApiResponse;
import com.masai.repositories.CustomerRepo;
import com.masai.services.CustomerService;

/**
 * @author tejas
 *
 */
@Service
public class CustomerServiceImplementation implements CustomerService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CustomerRepo customerRepo;

	@Override
	public CustomerDetailsResponseDto signUpCustomer(CustomerRequestDto customerRequestDto) {

		Customer customer = this.toCustomer(customerRequestDto);

		customer.setAccountCreatedDate(LocalDate.now());

		Customer savedCustomer = customerRepo.save(customer);

		return this.toCustomerDetailsResponseDto(savedCustomer);

	}

	// Make Sure you dont give Validation to Update

	@Override
	public CustomerDetailsResponseDto updateCustomerDetails(String contact,
			CustomerUpdateRequestDto customerUpdateRequestDto) throws ResourceNotFoundException {

		Customer customer = this.customerRepo.findByContact(contact)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "Contact Number", contact));

		if (customerUpdateRequestDto.getContact() == null) {
			customer.setContact(contact);
		} else {
			customer.setContact(customerUpdateRequestDto.getContact());
		}

		if (customerUpdateRequestDto.getDateOfBirth() == null) {
			customer.setDateOfBirth(customer.getDateOfBirth());
		} else {
			customer.setDateOfBirth(customerUpdateRequestDto.getDateOfBirth());
		}
		if (customerUpdateRequestDto.getEmail() == null) {
			customer.setEmail(customer.getEmail());
		} else {
			customer.setEmail(customerUpdateRequestDto.getEmail());
		}
		if (customerUpdateRequestDto.getFirstName() == null) {
			customer.setFirstName(customer.getFirstName());
		} else {
			customer.setFirstName(customerUpdateRequestDto.getFirstName());
		}
		if (customerUpdateRequestDto.getLastName() == null) {
			customer.setLastName(customer.getLastName());
		} else {
			customer.setLastName(customerUpdateRequestDto.getLastName());
		}
		if (customerUpdateRequestDto.getPassword() == null) {
			customer.setPassword(customer.getPassword());
		} else {
			customer.setPassword(customerUpdateRequestDto.getPassword());
		}

		Customer savedCustomer = this.customerRepo.save(customer);

		return this.toCustomerDetailsResponseDto(savedCustomer);
	}

	@Override
	public ApiResponse deleteCustomerAccount(String contact) throws ResourceNotFoundException {

		Customer customer = this.customerRepo.findByContact(contact)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "Contact Number", contact));

		this.customerRepo.delete(customer);

		return new ApiResponse(LocalDateTime.now(), "Account Deleted Successfully !", true);

	}

	@Override
	public List<CustomerResponseDto> getAllCustomerDetails() {

		List<Customer> list = this.customerRepo.findAll();

		return list.stream().map(this::toCustomerResponseDto).collect(Collectors.toList());
	}

	@Override
	public CustomerResponseDto getCustomer(String contact) throws ResourceNotFoundException {

		Customer customer = this.customerRepo.findByContact(contact)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "Contact Number", contact));

		return this.toCustomerResponseDto(customer);
	}

	// Model Mapper Methods

	private CustomerResponseDto toCustomerResponseDto(Customer customer) {

		return this.modelMapper.map(customer, CustomerResponseDto.class);
	}

	private CustomerDetailsResponseDto toCustomerDetailsResponseDto(Customer customer) {

		return this.modelMapper.map(customer, CustomerDetailsResponseDto.class);
	}

	private Customer toCustomer(CustomerRequestDto customerRequestDto) {

		return this.modelMapper.map(customerRequestDto, Customer.class);

	}

}
