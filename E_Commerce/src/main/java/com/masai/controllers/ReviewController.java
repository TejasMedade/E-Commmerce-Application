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
import com.masai.model.Review;
import com.masai.modelRequestDto.ReviewRequestDto;
import com.masai.modelResponseDto.CustomerDetailsResponseDto;
import com.masai.modelResponseDto.ReviewResponseDto;
import com.masai.payloads.ApiResponse;
import com.masai.payloads.AppConstants;
import com.masai.payloads.ReviewModelAssembler;
import com.masai.services.ReviewServices;

/**
 * @author tejas
 *
 */
@RestController
@RequestMapping("/bestbuy/reviews")
public class ReviewController {

	@Autowired
	private ReviewServices reviewServices;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ReviewModelAssembler reviewModelAssembler;

	@Autowired
	private PagedResourcesAssembler<Review> pagedResourcesAssembler;

	@GetMapping("/products/{productId}")
	public ResponseEntity<List<ReviewResponseDto>> getReviewsByProduct(@PathVariable("productId") Integer productId)
			throws ResourceNotFoundException {

		List<ReviewResponseDto> reviewsByProduct = this.reviewServices.getReviewsByProduct(productId);

		return new ResponseEntity<List<ReviewResponseDto>>(reviewsByProduct, HttpStatus.OK);
	}

	@GetMapping("/customers/{contact}")
	public ResponseEntity<List<ReviewResponseDto>> getReviewsByCustomer(@PathVariable("contact") String contact)
			throws ResourceNotFoundException {

		List<ReviewResponseDto> reviewsByCustomer = this.reviewServices.getReviewsByCustomer(contact);

		return new ResponseEntity<List<ReviewResponseDto>>(reviewsByCustomer, HttpStatus.OK);
	}

	@PostMapping("/products/{productId}/customers/{contact}")
	public ResponseEntity<ReviewResponseDto> addReview(@PathVariable("contact") String contact,
			@PathVariable("productId") Integer productId, @Valid @RequestParam String reviewRequestDto,
			@RequestParam(required = false) MultipartFile[] images)
			throws ResourceNotFoundException, ResourceNotAllowedException, IOException, FileTypeNotValidException {

		// converting String into JSON
		ReviewRequestDto review = objectMapper.readValue(reviewRequestDto, ReviewRequestDto.class);

		if (images == null) {

			ReviewResponseDto reviewResponseDto = this.reviewServices.addReview(contact, productId, review);

			// Self Link
			reviewResponseDto
					.add(linkTo(methodOn(ReviewController.class).getReviewById(reviewResponseDto.getReviewId()))
							.withSelfRel());

			// Customer Link
			CustomerDetailsResponseDto customer = reviewResponseDto.getCustomer();

			customer.add(linkTo(
					methodOn(CustomerController.class).getCustomerHandler(reviewResponseDto.getCustomer().getContact()))
					.withRel("customer"));

			return new ResponseEntity<ReviewResponseDto>(reviewResponseDto, HttpStatus.CREATED);
		} else {

			ReviewResponseDto reviewResponseDto = this.reviewServices.addReviewWithImages(contact, productId, review,
					images);

			// Self Link
			reviewResponseDto
					.add(linkTo(methodOn(ReviewController.class).getReviewById(reviewResponseDto.getReviewId()))
							.withSelfRel());

			// Customer Link
			CustomerDetailsResponseDto customer = reviewResponseDto.getCustomer();

			customer.add(linkTo(
					methodOn(CustomerController.class).getCustomerHandler(reviewResponseDto.getCustomer().getContact()))
					.withRel("customer"));

			return new ResponseEntity<ReviewResponseDto>(reviewResponseDto, HttpStatus.CREATED);
		}

	}

	@GetMapping("/dates/products/{productId}")
	public ResponseEntity<CollectionModel<ReviewResponseDto>> getAllReviewsByProductByDate(
			@PathVariable("productId") Integer productId,
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		Page<Review> pageResponse = this.reviewServices.getAllReviewsByProductOrderedByDate(productId, pageNumber,
				pageSize);

		PagedModel<ReviewResponseDto> model = pagedResourcesAssembler.toModel(pageResponse, reviewModelAssembler);

		// Collection List
		model.add(linkTo(methodOn(ReviewController.class).getAllReviews(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		for (ReviewResponseDto reviewResponseDto : model) {

			// Customer Link
			CustomerDetailsResponseDto customer = reviewResponseDto.getCustomer();

			customer.add(linkTo(
					methodOn(CustomerController.class).getCustomerHandler(reviewResponseDto.getCustomer().getContact()))
					.withRel("customer"));
		}

		return new ResponseEntity<CollectionModel<ReviewResponseDto>>(model, HttpStatus.OK);
	}

	@GetMapping("/ratings/products/{productId}")
	public ResponseEntity<CollectionModel<ReviewResponseDto>> getAllReviewsByProductOrderedByRating(
			@PathVariable("productId") Integer productId,
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.RATINGSORTDIRECTION, required = false) String sortDirection)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		Page<Review> pageResponse = this.reviewServices.getAllReviewsByProductOrderedByRating(productId, pageNumber,
				pageSize, sortDirection);

		PagedModel<ReviewResponseDto> model = pagedResourcesAssembler.toModel(pageResponse, reviewModelAssembler);

		// Collection List
		model.add(linkTo(methodOn(ReviewController.class).getAllReviews(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		for (ReviewResponseDto reviewResponseDto : model) {

			// Customer Link
			CustomerDetailsResponseDto customer = reviewResponseDto.getCustomer();

			customer.add(linkTo(
					methodOn(CustomerController.class).getCustomerHandler(reviewResponseDto.getCustomer().getContact()))
					.withRel("customer"));
		}

		return new ResponseEntity<CollectionModel<ReviewResponseDto>>(model, HttpStatus.OK);
		
	}

	@GetMapping("/ratings")
	public ResponseEntity<CollectionModel<ReviewResponseDto>> getAllReviewsSortedByRating(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.RATINGSORTDIRECTION, required = false) String sortDirection)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		Page<Review> pageResponse = this.reviewServices.getAllReviewsSortedByRating(pageNumber, pageSize,
				sortDirection);

		PagedModel<ReviewResponseDto> model = pagedResourcesAssembler.toModel(pageResponse, reviewModelAssembler);

		// Collection List
		model.add(linkTo(methodOn(ReviewController.class).getAllReviews(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		for (ReviewResponseDto reviewResponseDto : model) {

			// Customer Link
			CustomerDetailsResponseDto customer = reviewResponseDto.getCustomer();

			customer.add(linkTo(
					methodOn(CustomerController.class).getCustomerHandler(reviewResponseDto.getCustomer().getContact()))
					.withRel("customer"));
		}

		return new ResponseEntity<CollectionModel<ReviewResponseDto>>(model, HttpStatus.OK);
	}

	// method to serve images
	@GetMapping(value = "/images/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void serveImageHandler(@PathVariable("imageName") String imageName, HttpServletResponse response)
			throws IOException, ResourceNotFoundException, FileTypeNotValidException {

		this.reviewServices.serveReviewImage(imageName, response);

	}

	@DeleteMapping("/{reviewId}/delete")
	public ResponseEntity<ApiResponse> deleteReview(@PathVariable("reviewId") Integer reviewId)
			throws ResourceNotFoundException, IOException {

		ApiResponse apiResponse = this.reviewServices.deleteReview(reviewId);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.GONE);
	}

	@GetMapping("/{reviewId}")
	public ResponseEntity<ReviewResponseDto> getReviewById(@PathVariable("reviewId") Integer reviewId)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		ReviewResponseDto reviewResponseDto = this.reviewServices.getReviewById(reviewId);

		// Self Link
		reviewResponseDto.add(
				linkTo(methodOn(ReviewController.class).getReviewById(reviewResponseDto.getReviewId())).withSelfRel());

		// Customer Link
		CustomerDetailsResponseDto customer = reviewResponseDto.getCustomer();

		customer.add(linkTo(
				methodOn(CustomerController.class).getCustomerHandler(reviewResponseDto.getCustomer().getContact()))
				.withRel("customer"));

		// Collections Link

		reviewResponseDto.add(linkTo(methodOn(ReviewController.class).getAllReviews(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<ReviewResponseDto>(reviewResponseDto, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<CollectionModel<ReviewResponseDto>> getAllReviews(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize) {

		Page<Review> pageResponse = this.reviewServices.getAllReviews(pageNumber, pageSize);

		PagedModel<ReviewResponseDto> model = pagedResourcesAssembler.toModel(pageResponse, reviewModelAssembler);

		return new ResponseEntity<CollectionModel<ReviewResponseDto>>(model, HttpStatus.OK);
	}

}
