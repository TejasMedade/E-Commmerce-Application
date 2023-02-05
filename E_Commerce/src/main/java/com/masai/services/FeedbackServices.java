/**
 * 
 */
package com.masai.services;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.masai.exceptions.FileTypeNotValidException;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.model.Feedback;
import com.masai.modelRequestDto.FeedbackRequestDto;
import com.masai.modelResponseDto.FeedbackResponseDto;
import com.masai.payloads.ApiResponse;

/**
 * @author tejas
 *
 */
public interface FeedbackServices {

	FeedbackResponseDto getFeedbackById(Integer feedbackId) throws ResourceNotFoundException;

	Page<Feedback> getFeedbacksByRating(Integer page, Integer size, String sortDirection);

	Page<Feedback> getFeedbacksByDate(Integer page, Integer size, String sortDirection);

	void serveFeedBackImage(String imageName, HttpServletResponse response)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException;

	ApiResponse deleteFeedBackImage(String fileName) throws IOException;

	FeedbackResponseDto addFeedbackForDeliveredOrder(String contact, Integer orderId,
			FeedbackRequestDto feedbackRequestDto)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException;

	FeedbackResponseDto addFeedbackForReplacementOrder(String contact, Integer orderId,
			FeedbackRequestDto feedbackRequestDto)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException;

	FeedbackResponseDto addFeedbackForCancelledOrderWithImage(String contact, Integer orderId,
			FeedbackRequestDto feedbackRequestDto, MultipartFile image)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException;

	Page<Feedback> getAllFeedbacks(Integer pageNumber, Integer pageSize);

	FeedbackResponseDto addFeedbackForDeliveredOrderWithImage(String contact, Integer orderId,
			FeedbackRequestDto feedbackRequestDto, MultipartFile image)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException;

	FeedbackResponseDto addFeedbackForReplacementOrderWithImage(String contact, Integer orderId,
			FeedbackRequestDto feedbackRequestDto, MultipartFile image)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException;

	FeedbackResponseDto addFeedbackForCancelledOrder(String contact, Integer orderId,
			FeedbackRequestDto feedbackRequestDto)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException;

	Page<Feedback> getFeedbacksByCustomer(String contact, Integer page, Integer size, String sortDirection,
			String sortBy) throws ResourceNotFoundException;

	Page<Feedback> getFeedbacksByOrder(Integer orderId, Integer page, Integer size, String sortDirection, String sortBy)
			throws ResourceNotFoundException;

}
