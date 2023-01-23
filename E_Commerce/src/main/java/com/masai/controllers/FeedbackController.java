/**
 * 
 */
package com.masai.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
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
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.modelRequestDto.FeedbackRequestDto;
import com.masai.modelResponseDto.FeedbackResponseDto;
import com.masai.payloads.ApiResponse;
import com.masai.payloads.AppConstants;
import com.masai.payloads.PageResponse;
import com.masai.services.FeedbackServices;

/**
 * @author tejas
 *
 */
@RestController
@RequestMapping("bestbuy/feedbacks")
public class FeedbackController {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private FeedbackServices feedbackServices;

	@Value("${project.image}")
	private String path;

	@PostMapping("/customers/{contact}/orders/delievered/{orderId}")
	public ResponseEntity<FeedbackResponseDto> addFeedbackForDeliveredOrder(@PathVariable("contact") String contact,
			@PathVariable("orderId") Integer orderId, @Valid @RequestParam String feedbackRequestDto,
			@RequestParam(required = false) MultipartFile image)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException {

		// converting String into JSON
		FeedbackRequestDto feedback = objectMapper.readValue(feedbackRequestDto, FeedbackRequestDto.class);

		FeedbackResponseDto feedbackResponseDto = this.feedbackServices.addFeedbackForDeliveredOrder(contact, orderId,
				feedback, image);

		return new ResponseEntity<FeedbackResponseDto>(feedbackResponseDto, HttpStatus.ACCEPTED);
	}

	@PostMapping("/customers/{contact}/orders/cancelled/{orderId}")
	public ResponseEntity<FeedbackResponseDto> addFeedbackForCancelledOrder(@PathVariable("contact") String contact,
			@PathVariable("orderId") Integer orderId, @Valid @RequestParam String feedbackRequestDto,
			@RequestParam(required = false) MultipartFile image)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException {

		// converting String into JSON
		FeedbackRequestDto feedback = objectMapper.readValue(feedbackRequestDto, FeedbackRequestDto.class);

		if (image == null) {

			FeedbackResponseDto feedbackResponseDto = this.feedbackServices.addFeedbackForCancelledOrder(contact,
					orderId, feedback, image);

			return new ResponseEntity<FeedbackResponseDto>(feedbackResponseDto, HttpStatus.ACCEPTED);
		} else {

			FeedbackResponseDto feedbackResponseDto = this.feedbackServices
					.addFeedbackForCancelledOrderWithImage(contact, orderId, feedback, image);

			return new ResponseEntity<FeedbackResponseDto>(feedbackResponseDto, HttpStatus.ACCEPTED);
		}

	}

	@PostMapping("/customers/{contact}/orders/replacements/{orderId}")
	public ResponseEntity<FeedbackResponseDto> addFeedbackForReplacementOrder(@PathVariable("contact") String contact,
			@PathVariable("orderId") Integer orderId, @Valid @RequestParam String feedbackRequestDto,
			@RequestParam(required = false) MultipartFile image)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException {

		// converting String into JSON
		FeedbackRequestDto feedback = objectMapper.readValue(feedbackRequestDto, FeedbackRequestDto.class);

		FeedbackResponseDto feedbackResponseDto = this.feedbackServices.addFeedbackForReplacementOrder(contact, orderId,
				feedback, image);

		return new ResponseEntity<FeedbackResponseDto>(feedbackResponseDto, HttpStatus.ACCEPTED);
	}

	@GetMapping("/customers/{contact}")
	public ResponseEntity<List<FeedbackResponseDto>> getFeedbacksByCustomer(@PathVariable("contact") String contact)
			throws ResourceNotFoundException {

		List<FeedbackResponseDto> feedbacksByCustomer = this.feedbackServices.getFeedbacksByCustomer(contact);

		return new ResponseEntity<List<FeedbackResponseDto>>(feedbacksByCustomer, HttpStatus.OK);
	}

	@GetMapping("/orders/{orderId}")
	public ResponseEntity<FeedbackResponseDto> getFeedbacksByOrder(@PathVariable("orderId") Integer orderId)
			throws ResourceNotFoundException {

		FeedbackResponseDto feedbackResponseDto = this.feedbackServices.getFeedbacksByOrder(orderId);

		feedbackResponseDto.add(linkTo(methodOn(FeedbackController.class).getFeedbacksByOrder(orderId)).withSelfRel());
//		feedbackResponseDto.add(linkTo(methodOn(OrderController.class).getOrderById(orderId)).)

		// Link for Order
		// Link for all Feedback

		return new ResponseEntity<FeedbackResponseDto>(feedbackResponseDto, HttpStatus.OK);
	}

	@GetMapping("/ratings")
	public ResponseEntity<PageResponse> getFeedbacksByRating(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.RATINGSORTDIRECTION, required = false) String sortDirection) {

		PageResponse pageResponse = this.feedbackServices.getFeedbacksByRating(pageNumber, pageSize, sortDirection);

		return new ResponseEntity<PageResponse>(pageResponse, HttpStatus.OK);
	}

	@GetMapping("/dates")
	public ResponseEntity<PageResponse> getFeedbacksByDate(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.RATINGSORTDIRECTION, required = false) String sortDirection) {

		PageResponse pageResponse = this.feedbackServices.getFeedbacksByDate(pageNumber, pageSize, sortDirection);

		return new ResponseEntity<PageResponse>(pageResponse, HttpStatus.OK);

	}

	// method to serve images
	@GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void serveFeedBackImage(@PathVariable("imageName") String imageName, HttpServletResponse response)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException {

		this.feedbackServices.serveFeedBackImage(imageName, response);

	}

	// method to delete images
	@DeleteMapping("/images/{fileName}")
	public ResponseEntity<ApiResponse> deleteFeedBackImage(@PathVariable("fileName") String fileName)
			throws IOException {

		ApiResponse apiResponse = this.feedbackServices.deleteFeedBackImage(fileName);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<CollectionModel<FeedbackResponseDto>> getallFeedbacks() throws ResourceNotFoundException {

		List<FeedbackResponseDto> getallFeedbacks = this.feedbackServices.getallFeedbacks();

		for (FeedbackResponseDto feedbackResponseDto : getallFeedbacks) {

			feedbackResponseDto
					.add(linkTo(methodOn(FeedbackController.class).getFeedbackById(feedbackResponseDto.getFeedbackId()))
							.withSelfRel());

		}

		CollectionModel<FeedbackResponseDto> collectionModel = CollectionModel.of(getallFeedbacks);

		collectionModel.add(linkTo(methodOn(FeedbackController.class).getallFeedbacks()).withSelfRel());

		return new ResponseEntity<CollectionModel<FeedbackResponseDto>>(collectionModel, HttpStatus.OK);
	}

	@GetMapping("/{feedbackId}")
	public ResponseEntity<FeedbackResponseDto> getFeedbackById(@PathVariable("feedbackId") Integer feedbackId)
			throws ResourceNotFoundException {

		FeedbackResponseDto feedbackResponseDto = this.feedbackServices.getFeedbackById(feedbackId);

		feedbackResponseDto.add(linkTo(methodOn(FeedbackController.class).getFeedbackById(feedbackId)).withSelfRel());
		
		feedbackResponseDto.add(
				linkTo(methodOn(FeedbackController.class).getallFeedbacks()).withRel(IanaLinkRelations.COLLECTION));
		
		
		
		return new ResponseEntity<FeedbackResponseDto>(feedbackResponseDto, HttpStatus.OK);
	}

}
