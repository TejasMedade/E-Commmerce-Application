/**
 * 
 */
package com.masai.servicesImplementation;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.masai.exceptions.FileTypeNotValidException;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.model.Cart;
import com.masai.model.CartProductDetails;
import com.masai.model.Customer;
import com.masai.model.Image;
import com.masai.model.Role;
import com.masai.modelRequestDto.CustomerRequestDto;
import com.masai.modelRequestDto.CustomerUpdateRequestDto;
import com.masai.modelResponseDto.CustomerDetailsResponseDto;
import com.masai.modelResponseDto.CustomerResponseDto;
import com.masai.payloads.ApiResponse;
import com.masai.payloads.AppConstants;
import com.masai.payloads.ImageResponse;
import com.masai.repositories.CustomerRepo;
import com.masai.repositories.RoleRepo;
import com.masai.services.CustomerServices;
import com.masai.services.FileServices;

/**
 * @author tejas
 *
 */
@Service
public class CustomerServiceImplementation implements CustomerServices {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private FileServices fileServices;

	@Value("${project.image}")
	private String path;

	@Override
	public CustomerDetailsResponseDto registerCustomer(CustomerRequestDto customerRequestDto)
			throws ResourceNotFoundException {

		Cart cart = new Cart();

		List<CartProductDetails> listOfProducts = new ArrayList<>();

		cart.setCartQuantity(0);
		cart.setCartTotalAmount(0.0);
		cart.setListOfProducts(listOfProducts);

		Customer customer = this.toCustomer(customerRequestDto);

		customer.setCart(cart);

		Set<Role> roles = customer.getRoles();

		// Adding Role
		Role role = this.roleRepo.findById(AppConstants.ROLE_USER)
				.orElseThrow(() -> new ResourceNotFoundException("Role", "Role Id", AppConstants.ROLE_USER));

		roles.add(role);

		customer.setRoles(roles);

		// Encoding Password
		customer.setPassword(passwordEncoder.encode(customerRequestDto.getPassword()));

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
			customer.setPassword(passwordEncoder.encode(customerUpdateRequestDto.getLastName()));
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
	public Page<Customer> getAllCustomerDetails(Integer page, Integer size, String sortDirection, String sortBy) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(page, size, sort);

		return this.customerRepo.findAll(pageable);

	}

	@Override
	public CustomerResponseDto getCustomer(String contact) throws ResourceNotFoundException {

		Customer customer = this.customerRepo.findByContact(contact)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "Contact Number", contact));

		return this.toCustomerResponseDto(customer);
	}

	@Override
	public CustomerDetailsResponseDto updateCustomerImage(String contact, MultipartFile image)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException {

		if (!image.isEmpty()) {

			Customer customer = this.customerRepo.findByContact(contact)
					.orElseThrow(() -> new ResourceNotFoundException("Customer", "Contact Number", contact));

			ImageResponse imageResponse = this.fileServices.addImage(path, image);

			String url = "http://localhost:8088/bestbuy/customers/image/";

			Image file = new Image();

			file.setImageName(imageResponse.getFileName());
			file.setImageUrl(url.concat(imageResponse.getFileName()));

			customer.setImage(file);

			Customer savedCustomer = this.customerRepo.save(customer);

			return this.toCustomerDetailsResponseDto(savedCustomer);

		} else {
			throw new IllegalArgumentException("Customer Profile Photo Should Not be Null or Empty");

		}

	}

	@Override
	public List<CustomerResponseDto> searchByfirstName(String firstName) {

		List<Customer> findByFirstName = this.customerRepo.findByFirstName(firstName);

		return findByFirstName.stream().map(this::toCustomerResponseDto).collect(Collectors.toList());

	}

	@Override
	public List<CustomerResponseDto> searchBylastName(String lastName) {

		List<Customer> findByFirstName = this.customerRepo.findByLastName(lastName);

		return findByFirstName.stream().map(this::toCustomerResponseDto).collect(Collectors.toList());

	}

	@Override
	public List<CustomerResponseDto> searchByemailId(String email) {

		List<Customer> findByFirstName = this.customerRepo.findByEmail(email);

		return findByFirstName.stream().map(this::toCustomerResponseDto).collect(Collectors.toList());

	}

	@Override
	public List<CustomerResponseDto> searchByFirstAndLastName(String firstName, String lastName) {

		List<Customer> findByFirstNameAndLastName = this.customerRepo.findByFirstNameAndLastName(firstName, lastName);

		return findByFirstNameAndLastName.stream().map(this::toCustomerResponseDto).collect(Collectors.toList());
	}

	@Override
	public void serveCustomerImage(String imageName, HttpServletResponse response)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException {

		InputStream resource = this.fileServices.serveImage(path, imageName);

		response.setContentType(MediaType.IMAGE_JPEG_VALUE);

		StreamUtils.copy(resource, response.getOutputStream());

	}

	@Override
	public ApiResponse deleteCustomerImage(String fileName) throws IOException {

		boolean delete = this.fileServices.delete(fileName);

		if (delete) {
			return new ApiResponse(LocalDateTime.now(), "Image File Deleted Successfully !", true);
		} else {
			return new ApiResponse(LocalDateTime.now(), "Requested Image File Does Not Exist !", false);
		}

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
