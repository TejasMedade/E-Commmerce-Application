/**
 * 
 */
package com.masai.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.masai.exceptions.FileTypeNotValidException;
import com.masai.exceptions.ResourceNotAllowedException;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.model.Feedback;
import com.masai.model.OrderProductDetails;
import com.masai.modelRequestDto.FeedbackRequestDto;
import com.masai.modelResponseDto.CustomerDetailsResponseDto;
import com.masai.modelResponseDto.FeedbackResponseDto;
import com.masai.modelResponseDto.OrderDetailsResponseDto;
import com.masai.payloads.ApiResponse;
import com.masai.payloads.AppConstants;
import com.masai.payloads.FeedbackModelAssembler;
import com.masai.services.FeedbackServices;

/**
 * @author tejas
 *
 */
@RestController
@RequestMapping("/bestbuy/feedbacks")
public class FeedbackController {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private FeedbackServices feedbackServices;

	@Value("${project.image}")
	private String path;

	@Autowired
	private FeedbackModelAssembler feedbackModelAssembler;

	@Autowired
	private PagedResourcesAssembler<Feedback> pagedResourcesAssembler;

	@PostMapping("/customers/{contact}/orders/{orderId}/deliever")
	public ResponseEntity<FeedbackResponseDto> addFeedbacksForDeliveredOrdersHandler(
			@PathVariable("contact") String contact, @PathVariable("orderId") Integer orderId,
			@Valid @RequestParam String feedbackRequestDto, @RequestParam(required = false) MultipartFile image)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException, ResourceNotAllowedException {

		// converting String into JSON
		FeedbackRequestDto feedback = objectMapper.readValue(feedbackRequestDto, FeedbackRequestDto.class);

		if (image == null) {

			FeedbackResponseDto feedbackResponseDto = this.feedbackServices.addFeedbackForDeliveredOrder(contact,
					orderId, feedback);

			// Self Link
			feedbackResponseDto
					.add(linkTo(methodOn(FeedbackController.class).getFeedbackById(feedbackResponseDto.getFeedbackId()))
							.withSelfRel());

			// Collection Link
			feedbackResponseDto.add(linkTo(methodOn(FeedbackController.class).getAllFeedbacks(null, null))
					.withRel(IanaLinkRelations.COLLECTION));

			// Customer Link
			CustomerDetailsResponseDto customer = feedbackResponseDto.getCustomer();

			customer.add(linkTo(methodOn(CustomerController.class)
					.getCustomerHandler(feedbackResponseDto.getCustomer().getContact())).withRel("customer"));

			// Order Link
			OrderDetailsResponseDto order = feedbackResponseDto.getOrder();

			order.add(linkTo(methodOn(OrderController.class).getOrderById(feedbackResponseDto.getOrder().getOrderId()))
					.withRel("order"));

			return new ResponseEntity<FeedbackResponseDto>(feedbackResponseDto, HttpStatus.CREATED);
		} else {

			FeedbackResponseDto feedbackResponseDto = this.feedbackServices
					.addFeedbackForDeliveredOrderWithImage(contact, orderId, feedback, image);

			// Self Link
			feedbackResponseDto
					.add(linkTo(methodOn(FeedbackController.class).getFeedbackById(feedbackResponseDto.getFeedbackId()))
							.withSelfRel());

			// Collection Link
			feedbackResponseDto.add(linkTo(methodOn(FeedbackController.class).getAllFeedbacks(null, null))
					.withRel(IanaLinkRelations.COLLECTION));

			// Customer Link
			CustomerDetailsResponseDto customer = feedbackResponseDto.getCustomer();

			customer.add(linkTo(methodOn(CustomerController.class)
					.getCustomerHandler(feedbackResponseDto.getCustomer().getContact())).withRel("customer"));

			// Order Link
			OrderDetailsResponseDto order = feedbackResponseDto.getOrder();

			order.add(linkTo(methodOn(OrderController.class).getOrderById(feedbackResponseDto.getOrder().getOrderId()))
					.withRel("order"));

			return new ResponseEntity<FeedbackResponseDto>(feedbackResponseDto, HttpStatus.CREATED);
		}

	}

	@PostMapping("/customers/{contact}/orders/{orderId}/cancel")
	public ResponseEntity<FeedbackResponseDto> addFeedbacksForCancelledOrderHandler(
			@PathVariable("contact") String contact, @PathVariable("orderId") Integer orderId,
			@Valid @RequestParam String feedbackRequestDto, @RequestParam(required = false) MultipartFile image)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException, ResourceNotAllowedException {

		// converting String into JSON
		FeedbackRequestDto feedback = objectMapper.readValue(feedbackRequestDto, FeedbackRequestDto.class);

		if (image == null) {

			FeedbackResponseDto feedbackResponseDto = this.feedbackServices.addFeedbackForCancelledOrder(contact,
					orderId, feedback);

			// Self Link
			feedbackResponseDto
					.add(linkTo(methodOn(FeedbackController.class).getFeedbackById(feedbackResponseDto.getFeedbackId()))
							.withSelfRel());

			// Collection Link
			feedbackResponseDto.add(linkTo(methodOn(FeedbackController.class).getAllFeedbacks(null, null))
					.withRel(IanaLinkRelations.COLLECTION));

			// Customer Link
			CustomerDetailsResponseDto customer = feedbackResponseDto.getCustomer();

			customer.add(linkTo(methodOn(CustomerController.class)
					.getCustomerHandler(feedbackResponseDto.getCustomer().getContact())).withRel("customer"));

			// Order Link
			OrderDetailsResponseDto order = feedbackResponseDto.getOrder();

			order.add(linkTo(methodOn(OrderController.class).getOrderById(feedbackResponseDto.getOrder().getOrderId()))
					.withRel("order"));

			return new ResponseEntity<FeedbackResponseDto>(feedbackResponseDto, HttpStatus.CREATED);
		} else {

			FeedbackResponseDto feedbackResponseDto = this.feedbackServices
					.addFeedbackForCancelledOrderWithImage(contact, orderId, feedback, image);

			// Self Link
			feedbackResponseDto
					.add(linkTo(methodOn(FeedbackController.class).getFeedbackById(feedbackResponseDto.getFeedbackId()))
							.withSelfRel());

			// Collection Link
			feedbackResponseDto.add(linkTo(methodOn(FeedbackController.class).getAllFeedbacks(null, null))
					.withRel(IanaLinkRelations.COLLECTION));

			// Customer Link
			CustomerDetailsResponseDto customer = feedbackResponseDto.getCustomer();

			customer.add(linkTo(methodOn(CustomerController.class)
					.getCustomerHandler(feedbackResponseDto.getCustomer().getContact())).withRel("customer"));

			// Order Link
			OrderDetailsResponseDto order = feedbackResponseDto.getOrder();

			order.add(linkTo(methodOn(OrderController.class).getOrderById(feedbackResponseDto.getOrder().getOrderId()))
					.withRel("order"));

			return new ResponseEntity<FeedbackResponseDto>(feedbackResponseDto, HttpStatus.CREATED);
		}

	}

	@PostMapping("/customers/{contact}/orders/{orderId}/replace")
	public ResponseEntity<FeedbackResponseDto> addFeedbacksForReplacementOrderHandler(
			@PathVariable("contact") String contact, @PathVariable("orderId") Integer orderId,
			@Valid @RequestParam String feedbackRequestDto, @RequestParam(required = false) MultipartFile image)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException, ResourceNotAllowedException {

		// converting String into JSON
		FeedbackRequestDto feedback = objectMapper.readValue(feedbackRequestDto, FeedbackRequestDto.class);

		if (image == null) {

			FeedbackResponseDto feedbackResponseDto = this.feedbackServices.addFeedbackForReplacementOrder(contact,
					orderId, feedback);

			// Self Link
			feedbackResponseDto
					.add(linkTo(methodOn(FeedbackController.class).getFeedbackById(feedbackResponseDto.getFeedbackId()))
							.withSelfRel());

			// Collection Link
			feedbackResponseDto.add(linkTo(methodOn(FeedbackController.class).getAllFeedbacks(null, null))
					.withRel(IanaLinkRelations.COLLECTION));

			// Customer Link
			CustomerDetailsResponseDto customer = feedbackResponseDto.getCustomer();

			customer.add(linkTo(methodOn(CustomerController.class)
					.getCustomerHandler(feedbackResponseDto.getCustomer().getContact())).withRel("customer"));

			// Order Link
			OrderDetailsResponseDto order = feedbackResponseDto.getOrder();

			order.add(linkTo(methodOn(OrderController.class).getOrderById(feedbackResponseDto.getOrder().getOrderId()))
					.withRel("order"));

			return new ResponseEntity<FeedbackResponseDto>(feedbackResponseDto, HttpStatus.CREATED);
		} else {

			FeedbackResponseDto feedbackResponseDto = this.feedbackServices
					.addFeedbackForReplacementOrderWithImage(contact, orderId, feedback, image);

			// Self Link
			feedbackResponseDto
					.add(linkTo(methodOn(FeedbackController.class).getFeedbackById(feedbackResponseDto.getFeedbackId()))
							.withSelfRel());

			// Collection Link
			feedbackResponseDto.add(linkTo(methodOn(FeedbackController.class).getAllFeedbacks(null, null))
					.withRel(IanaLinkRelations.COLLECTION));

			// Customer Link
			CustomerDetailsResponseDto customer = feedbackResponseDto.getCustomer();

			customer.add(linkTo(methodOn(CustomerController.class)
					.getCustomerHandler(feedbackResponseDto.getCustomer().getContact())).withRel("customer"));

			// Order Link
			OrderDetailsResponseDto order = feedbackResponseDto.getOrder();

			order.add(linkTo(methodOn(OrderController.class).getOrderById(feedbackResponseDto.getOrder().getOrderId()))
					.withRel("order"));

			return new ResponseEntity<FeedbackResponseDto>(feedbackResponseDto, HttpStatus.CREATED);
		}

	}

	@GetMapping("/customers/{contact}")
	public ResponseEntity<CollectionModel<FeedbackResponseDto>> getAllFeedbacksByCustomer(
			@PathVariable("contact") String contact,
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.RATINGSORTDIRECTION, required = false) String sortDirection,
			@RequestParam(defaultValue = AppConstants.FEEDBACKSORTBY, required = false) String sortBy
			)

			throws ResourceNotFoundException, ResourceNotAllowedException {

		Page<Feedback> pageResponse = this.feedbackServices.getFeedbacksByCustomer(contact, pageNumber, pageSize,
				sortDirection, sortBy);

		PagedModel<FeedbackResponseDto> model = pagedResourcesAssembler.toModel(pageResponse, feedbackModelAssembler);

		// Collection List
		model.add(linkTo(methodOn(FeedbackController.class).getAllFeedbacks(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		for (FeedbackResponseDto feedbackResponseDto : model) {

			// Customer Link
			CustomerDetailsResponseDto customer = feedbackResponseDto.getCustomer();

			customer.add(linkTo(methodOn(CustomerController.class)
					.getCustomerHandler(feedbackResponseDto.getCustomer().getContact())).withRel("customer"));

			// Order Link
			OrderDetailsResponseDto order = feedbackResponseDto.getOrder();

			order.add(linkTo(methodOn(OrderController.class).getOrderById(feedbackResponseDto.getOrder().getOrderId()))
					.withRel("order"));

		}

		return new ResponseEntity<CollectionModel<FeedbackResponseDto>>(model, HttpStatus.OK);
	}

	@GetMapping("/orders/{orderId}")
	public ResponseEntity<CollectionModel<FeedbackResponseDto>> getAllFeedbacksByOrder(
			@PathVariable("orderId") Integer orderId,
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.RATINGSORTDIRECTION, required = false) String sortDirection,
			@RequestParam(defaultValue = AppConstants.FEEDBACKSORTBY, required = false) String sortBy)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		Page<Feedback> pageResponse = this.feedbackServices.getFeedbacksByOrder(orderId, pageNumber, pageSize,
				sortDirection, sortDirection);

		PagedModel<FeedbackResponseDto> model = pagedResourcesAssembler.toModel(pageResponse, feedbackModelAssembler);

		// Collection List
		model.add(linkTo(methodOn(FeedbackController.class).getAllFeedbacks(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		for (FeedbackResponseDto feedbackResponseDto : model) {

			// Customer Link
			CustomerDetailsResponseDto customer = feedbackResponseDto.getCustomer();

			customer.add(linkTo(methodOn(CustomerController.class)
					.getCustomerHandler(feedbackResponseDto.getCustomer().getContact())).withRel("customer"));

			// Order Link
			OrderDetailsResponseDto order = feedbackResponseDto.getOrder();

			order.add(linkTo(methodOn(OrderController.class).getOrderById(feedbackResponseDto.getOrder().getOrderId()))
					.withRel("order"));

		}

		return new ResponseEntity<CollectionModel<FeedbackResponseDto>>(model, HttpStatus.OK);
	}

	@GetMapping("/sortby/ratings")
	public ResponseEntity<CollectionModel<FeedbackResponseDto>> getAllFeedbacksByRating(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.RATINGSORTDIRECTION, required = false) String sortDirection)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		Page<Feedback> pageResponse = this.feedbackServices.getFeedbacksByRating(pageNumber, pageSize, sortDirection);

		PagedModel<FeedbackResponseDto> model = pagedResourcesAssembler.toModel(pageResponse, feedbackModelAssembler);

		// Collection List
		model.add(linkTo(methodOn(FeedbackController.class).getAllFeedbacks(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		for (FeedbackResponseDto feedbackResponseDto : model) {

			// Customer Link
			CustomerDetailsResponseDto customer = feedbackResponseDto.getCustomer();

			customer.add(linkTo(methodOn(CustomerController.class)
					.getCustomerHandler(feedbackResponseDto.getCustomer().getContact())).withRel("customer"));

			feedbackResponseDto.setCustomer(customer);

			// Order Link
			OrderDetailsResponseDto order = feedbackResponseDto.getOrder();

			order.add(linkTo(methodOn(OrderController.class).getOrderById(feedbackResponseDto.getOrder().getOrderId()))
					.withRel("order"));

			feedbackResponseDto.setOrder(order);
		}

		return new ResponseEntity<CollectionModel<FeedbackResponseDto>>(model, HttpStatus.OK);
	}

	@GetMapping("/sortby/dates")
	public ResponseEntity<CollectionModel<FeedbackResponseDto>> getAllFeedbacksByDate(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.RATINGSORTDIRECTION, required = false) String sortDirection)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		Page<Feedback> pageResponse = this.feedbackServices.getFeedbacksByDate(pageNumber, pageSize, sortDirection);

		PagedModel<FeedbackResponseDto> model = pagedResourcesAssembler.toModel(pageResponse, feedbackModelAssembler);

		// Collection List
		model.add(linkTo(methodOn(FeedbackController.class).getAllFeedbacks(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		for (FeedbackResponseDto feedbackResponseDto : model) {

			// Customer Link
			CustomerDetailsResponseDto customer = feedbackResponseDto.getCustomer();

			customer.add(linkTo(methodOn(CustomerController.class)
					.getCustomerHandler(feedbackResponseDto.getCustomer().getContact())).withRel("customer"));

			feedbackResponseDto.setCustomer(customer);

			// Order Link
			OrderDetailsResponseDto order = feedbackResponseDto.getOrder();

			order.add(linkTo(methodOn(OrderController.class).getOrderById(feedbackResponseDto.getOrder().getOrderId()))
					.withRel("order"));

			feedbackResponseDto.setOrder(order);
		}

		return new ResponseEntity<CollectionModel<FeedbackResponseDto>>(model, HttpStatus.OK);

	}

	// method to serve images
	@GetMapping(value = "/images/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void serveFeedBackImage(@PathVariable("imageName") String imageName, HttpServletResponse response)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException {

		this.feedbackServices.serveFeedBackImage(imageName, response);

	}

	// method to delete images
	@DeleteMapping("/images/{imageName}")
	public ResponseEntity<ApiResponse> deleteFeedBackImage(@PathVariable("imageName") String imageName)
			throws IOException {

		ApiResponse apiResponse = this.feedbackServices.deleteFeedBackImage(imageName);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.GONE);
	}

	@GetMapping("/")
	public ResponseEntity<CollectionModel<FeedbackResponseDto>> getAllFeedbacks(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize)
			throws ResourceNotFoundException {

		Page<Feedback> getAllFeedbacks = this.feedbackServices.getAllFeedbacks(pageNumber, pageSize);

		PagedModel<FeedbackResponseDto> model = pagedResourcesAssembler.toModel(getAllFeedbacks,
				feedbackModelAssembler);

		return new ResponseEntity<CollectionModel<FeedbackResponseDto>>(model, HttpStatus.OK);
	}

	@GetMapping("/{feedbackId}")
	public ResponseEntity<FeedbackResponseDto> getFeedbackById(@PathVariable("feedbackId") Integer feedbackId)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		FeedbackResponseDto feedbackResponseDto = this.feedbackServices.getFeedbackById(feedbackId);

		// Self Link
		feedbackResponseDto.add(linkTo(methodOn(FeedbackController.class).getFeedbackById(feedbackId)).withSelfRel());

		// Collection Link
		feedbackResponseDto.add(linkTo(methodOn(FeedbackController.class).getAllFeedbacks(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		// Customer Link
		CustomerDetailsResponseDto customer = feedbackResponseDto.getCustomer();

		customer.add(linkTo(
				methodOn(CustomerController.class).getCustomerHandler(feedbackResponseDto.getCustomer().getContact()))
				.withRel("customer"));

		feedbackResponseDto.setCustomer(customer);

		// Order Link
		OrderDetailsResponseDto order = feedbackResponseDto.getOrder();

		order.add(linkTo(methodOn(OrderController.class).getOrderById(feedbackResponseDto.getOrder().getOrderId()))
				.withRel("order"));

		List<OrderProductDetails> listOfProducts = feedbackResponseDto.getOrder().getListOfProducts();

		for (OrderProductDetails o : listOfProducts) {

			o.add(linkTo(methodOn(ProductController.class).getProductByIdHandler(o.getProductId())).withSelfRel());

		}

		order.setListOfProducts(listOfProducts);
		feedbackResponseDto.setOrder(order);

		return new ResponseEntity<FeedbackResponseDto>(feedbackResponseDto, HttpStatus.OK);
	}

}
