/**
 * 
 */
package com.masai.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exceptions.ResourceNotFoundException;
import com.masai.modelRequestDto.FeedbackRequestDto;
import com.masai.modelResponseDto.FeedbackResponseDto;
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
	private FeedbackServices feedbackServices;

	@PostMapping("/customers/{contact}/orders/delievered/{orderId}")
	public ResponseEntity<FeedbackResponseDto> addFeedbackForDeliveredOrder(@PathVariable("contact") String contact,
			@PathVariable("orderId") Integer orderId, FeedbackRequestDto feedbackRequestDto)
			throws ResourceNotFoundException {

		FeedbackResponseDto feedbackResponseDto = this.feedbackServices.addFeedbackForDeliveredOrder(contact, orderId,
				feedbackRequestDto);

		return new ResponseEntity<FeedbackResponseDto>(feedbackResponseDto, HttpStatus.ACCEPTED);
	}

	@PostMapping("/customers/{contact}/orders/cancelled/{orderId}")
	public ResponseEntity<FeedbackResponseDto> addFeedbackForCancelledOrder(@PathVariable("contact") String contact,
			@PathVariable("orderId") Integer orderId, FeedbackRequestDto feedbackRequestDto)
			throws ResourceNotFoundException {

		FeedbackResponseDto feedbackResponseDto = this.feedbackServices.addFeedbackForCancelledOrder(contact, orderId,
				feedbackRequestDto);

		return new ResponseEntity<FeedbackResponseDto>(feedbackResponseDto, HttpStatus.ACCEPTED);
	}

	@PostMapping("/customers/{contact}/orders/replacements/{orderId}")
	public ResponseEntity<FeedbackResponseDto> addFeedbackForReplacementOrder(@PathVariable("contact") String contact,
			@PathVariable("orderId") Integer orderId, FeedbackRequestDto feedbackRequestDto)
			throws ResourceNotFoundException {

		FeedbackResponseDto feedbackResponseDto = this.feedbackServices.addFeedbackForReplacementOrder(contact, orderId,
				feedbackRequestDto);

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

}
