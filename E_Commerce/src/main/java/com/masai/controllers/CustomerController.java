/**
 * 
 */
package com.masai.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.masai.exceptions.FileTypeNotValidException;
import com.masai.exceptions.ResourceNotAllowedException;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.model.Customer;
import com.masai.modelRequestDto.CustomerUpdateRequestDto;
import com.masai.modelResponseDto.CustomerDetailsResponseDto;
import com.masai.modelResponseDto.CustomerResponseDto;
import com.masai.payloads.ApiResponse;
import com.masai.payloads.AppConstants;
import com.masai.payloads.CustomerResponseModelAssembler;
import com.masai.services.CustomerServices;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author tejas
 *
 */

@RestController
@RequestMapping("/bestbuy/customers")
public class CustomerController {

	@Autowired
	private CustomerServices customerService;

	@Autowired
	private CustomerResponseModelAssembler customerResponseModelAssembler;

	@Autowired
	private PagedResourcesAssembler<Customer> pagedResourcesAssembler;

	@PutMapping("/{contact}/image")
	public ResponseEntity<CustomerDetailsResponseDto> updateCustomerImageHandler(
			@PathVariable("contact") String contact, @RequestParam MultipartFile image)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException, ResourceNotAllowedException {

		CustomerDetailsResponseDto updateCustomerImage = this.customerService.updateCustomerImage(contact, image);

		// Self Link
		updateCustomerImage.add(
				linkTo(methodOn(CustomerController.class).updateCustomerImageHandler(contact, image)).withSelfRel());

		// Customer Link
		updateCustomerImage
				.add(linkTo(methodOn(CustomerController.class).getCustomerHandler(updateCustomerImage.getContact()))
						.withRel("customer"));

		// Cart
		updateCustomerImage.add(linkTo(methodOn(CartController.class).getCartHandler(contact)).withRel("cart"));

		// Orders
		updateCustomerImage.add(linkTo(methodOn(OrderController.class).getAllOrdersByCustomer(contact, null, null,
				AppConstants.SORTDIRECTION, AppConstants.CUSTOMERSORTBY)).withRel("orders"));

		// Review
		updateCustomerImage
				.add(linkTo(methodOn(ReviewController.class).getReviewsByCustomer(contact)).withRel("reviews"));

		// Feedbacks
		updateCustomerImage.add(linkTo(methodOn(FeedbackController.class).getAllFeedbacksByCustomer(contact, null, null,
				AppConstants.SORTDIRECTION, AppConstants.FEEDBACKSORTBY)).withRel("feedbacks"));

		return new ResponseEntity<CustomerDetailsResponseDto>(updateCustomerImage, HttpStatus.OK);
	}

	@PutMapping("/{contact}/update")
	public ResponseEntity<CustomerDetailsResponseDto> updateCustomerDetailsHandler(
			@PathVariable("contact") String contact,
			@Valid @RequestBody CustomerUpdateRequestDto customerUpdateRequestDto)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		CustomerDetailsResponseDto updateCustomerDetails = this.customerService.updateCustomerDetails(contact,
				customerUpdateRequestDto);

		// Self Link
		updateCustomerDetails.add(
				linkTo(methodOn(CustomerController.class).updateCustomerDetailsHandler(contact, null)).withSelfRel());

		// Customer Link
		updateCustomerDetails
				.add(linkTo(methodOn(CustomerController.class).getCustomerHandler(updateCustomerDetails.getContact()))
						.withRel("customer"));

		// Cart
		updateCustomerDetails.add(linkTo(methodOn(CartController.class).getCartHandler(contact)).withRel("cart"));

		// Orders
		updateCustomerDetails.add(linkTo(methodOn(OrderController.class).getAllOrdersByCustomer(contact, null, null,
				AppConstants.SORTDIRECTION, AppConstants.ORDERSORTBY)).withRel("orders"));

		// Review
		updateCustomerDetails
				.add(linkTo(methodOn(ReviewController.class).getReviewsByCustomer(contact)).withRel("reviews"));

		// Feedbacks
		updateCustomerDetails.add(linkTo(methodOn(FeedbackController.class).getAllFeedbacksByCustomer(contact, null,
				null, AppConstants.SORTDIRECTION, AppConstants.FEEDBACKSORTBY)).withRel("feedbacks"));

		return new ResponseEntity<CustomerDetailsResponseDto>(updateCustomerDetails, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{contact}/delete")
	public ResponseEntity<ApiResponse> deleteCustomerAccountHandler(@PathVariable("contact") String contact)
			throws ResourceNotFoundException {

		ApiResponse deleteCustomerAccount = this.customerService.deleteCustomerAccount(contact);

		return new ResponseEntity<ApiResponse>(deleteCustomerAccount, HttpStatus.GONE);
	}

	@GetMapping("/")
	public ResponseEntity<CollectionModel<CustomerResponseDto>> getAllCustomerDetailsHandler(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.SORTDIRECTION, required = false) String sortDirection,
			@RequestParam(defaultValue = AppConstants.CUSTOMERSORTBY, required = false) String sortBy) {

		Page<Customer> pageResponse = this.customerService.getAllCustomerDetails(pageNumber, pageSize, sortDirection,
				sortBy);

		PagedModel<CustomerResponseDto> model = pagedResourcesAssembler.toModel(pageResponse,
				customerResponseModelAssembler);

		// Collection
		model.add(linkTo(methodOn(CustomerController.class).getAllCustomerDetailsHandler(pageNumber, pageSize,
				sortDirection, sortBy)).withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CollectionModel<CustomerResponseDto>>(model, HttpStatus.OK);
	}

	@GetMapping("/{contact}")
	public ResponseEntity<CustomerResponseDto> getCustomerHandler(@PathVariable("contact") String contact)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		CustomerResponseDto customer = this.customerService.getCustomer(contact);

		// Collection
		customer.add(linkTo(methodOn(CustomerController.class).getAllCustomerDetailsHandler(null, null,
				AppConstants.SORTDIRECTION, AppConstants.CUSTOMERSORTBY)).withRel(IanaLinkRelations.COLLECTION));

		// Cart
		customer.add(linkTo(methodOn(CartController.class).getCartHandler(contact)).withRel("cart"));

		// Orders
		customer.add(linkTo(methodOn(OrderController.class).getAllOrdersByCustomer(contact, null, null,
				AppConstants.SORTDIRECTION, AppConstants.ORDERSORTBY)).withRel("orders"));

		// Review
		customer.add(linkTo(methodOn(ReviewController.class).getReviewsByCustomer(contact)).withRel("reviews"));

		// Feedbacks
		customer.add(linkTo(methodOn(FeedbackController.class).getAllFeedbacksByCustomer(contact, null, null,
				AppConstants.SORTDIRECTION, AppConstants.FEEDBACKSORTBY)).withRel("feedbacks"));

		return new ResponseEntity<CustomerResponseDto>(customer, HttpStatus.OK);
	}

	@GetMapping("/search/{firstname}")
	public ResponseEntity<CollectionModel<CustomerResponseDto>> searchByfirstNameHandler(
			@PathVariable("firstname") String firstName) throws ResourceNotFoundException, ResourceNotAllowedException {

		List<CustomerResponseDto> pageResponse = this.customerService.searchByfirstName(firstName);

		for (CustomerResponseDto customerResponseDto : pageResponse) {

			customerResponseDto
					.add(linkTo(methodOn(CustomerController.class).getCustomerHandler(customerResponseDto.getContact()))
							.withRel("customer"));

		}

		CollectionModel<CustomerResponseDto> collectionModel = CollectionModel.of(pageResponse);

		// Collection
		collectionModel.add(linkTo(methodOn(CustomerController.class).getAllCustomerDetailsHandler(null, null,
				AppConstants.SORTDIRECTION, AppConstants.CUSTOMERSORTBY)).withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CollectionModel<CustomerResponseDto>>(collectionModel, HttpStatus.OK);

	}

	@GetMapping("/search/{lastname}")
	public ResponseEntity<CollectionModel<CustomerResponseDto>> searchBylastName(
			@PathVariable("lastname") String lastName) throws ResourceNotFoundException, ResourceNotAllowedException {

		List<CustomerResponseDto> pageResponse = this.customerService.searchBylastName(lastName);

		for (CustomerResponseDto customerResponseDto : pageResponse) {

			customerResponseDto
					.add(linkTo(methodOn(CustomerController.class).getCustomerHandler(customerResponseDto.getContact()))
							.withRel("customer"));
		}

		CollectionModel<CustomerResponseDto> collectionModel = CollectionModel.of(pageResponse);

		// Collection
		collectionModel.add(linkTo(methodOn(CustomerController.class).getAllCustomerDetailsHandler(null, null,
				AppConstants.SORTDIRECTION, AppConstants.CUSTOMERSORTBY)).withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CollectionModel<CustomerResponseDto>>(collectionModel, HttpStatus.OK);
	}

	@GetMapping("/search/{email}")
	public ResponseEntity<CollectionModel<CustomerResponseDto>> searchByEmailId(@PathVariable("email") String email)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		List<CustomerResponseDto> pageResponse = this.customerService.searchByemailId(email);

		for (CustomerResponseDto customerResponseDto : pageResponse) {

			customerResponseDto
					.add(linkTo(methodOn(CustomerController.class).getCustomerHandler(customerResponseDto.getContact()))
							.withRel("customer"));
		}

		CollectionModel<CustomerResponseDto> collectionModel = CollectionModel.of(pageResponse);

		// Collection
		collectionModel.add(linkTo(methodOn(CustomerController.class).getAllCustomerDetailsHandler(null, null,
				AppConstants.SORTDIRECTION, AppConstants.CUSTOMERSORTBY)).withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CollectionModel<CustomerResponseDto>>(collectionModel, HttpStatus.OK);

	}

	@GetMapping("/search/first/last")
	public ResponseEntity<CollectionModel<CustomerResponseDto>> searchByFirstAndLastName(String firstName,
			String lastName) throws ResourceNotFoundException, ResourceNotAllowedException {

		List<CustomerResponseDto> pageResponse = this.customerService.searchByFirstAndLastName(firstName, lastName);

		for (CustomerResponseDto customerResponseDto : pageResponse) {

			customerResponseDto
					.add(linkTo(methodOn(CustomerController.class).getCustomerHandler(customerResponseDto.getContact()))
							.withRel("customer"));
		}

		CollectionModel<CustomerResponseDto> collectionModel = CollectionModel.of(pageResponse);

		// Collection
		collectionModel.add(linkTo(methodOn(CustomerController.class).getAllCustomerDetailsHandler(null, null,
				AppConstants.SORTDIRECTION, AppConstants.CUSTOMERSORTBY)).withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CollectionModel<CustomerResponseDto>>(collectionModel, HttpStatus.OK);

	}

	// method to serve images
	@GetMapping(value = "/images/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void serveImageHandler(@PathVariable("imageName") String imageName, HttpServletResponse response)
			throws IOException, ResourceNotFoundException, FileTypeNotValidException {

		this.customerService.serveCustomerImage(imageName, response);

	}

	// method to delete images
	@DeleteMapping("/images/{imageName}/delete")
	public ResponseEntity<ApiResponse> deleteImage(@PathVariable("imageName") String imageName) throws IOException {

		ApiResponse apiResponse = this.customerService.deleteCustomerImage(imageName);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.GONE);
	}
}
