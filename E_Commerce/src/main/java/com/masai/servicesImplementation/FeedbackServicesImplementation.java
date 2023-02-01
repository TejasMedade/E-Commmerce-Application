/**
 * 
 */
package com.masai.servicesImplementation;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.masai.exceptions.FileTypeNotValidException;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.model.Customer;
import com.masai.model.Feedback;
import com.masai.model.Image;
import com.masai.model.Order;
import com.masai.modelRequestDto.FeedbackRequestDto;
import com.masai.modelResponseDto.FeedbackResponseDto;
import com.masai.payloads.ApiResponse;
import com.masai.payloads.ImageResponse;
import com.masai.repositories.CustomerRepo;
import com.masai.repositories.FeedbackRepo;
import com.masai.repositories.OrderRepo;
import com.masai.services.FeedbackServices;
import com.masai.services.FileServices;

/**
 * @author tejas
 *
 */

@Service
public class FeedbackServicesImplementation implements FeedbackServices {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private FeedbackRepo feedbackRepo;

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private FileServices fileServices;

	@Value("${project.image}")
	private String path;

	@Override
	public FeedbackResponseDto addFeedbackForDeliveredOrder(String contact, Integer orderId,
			FeedbackRequestDto feedbackRequestDto)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException {

		Order order = this.orderRepo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order", "Order Id", orderId));

		Customer customer = this.customerRepo.findByContact(contact)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "Contact Number", contact));

		Image feedbackImage;

		feedbackImage = new Image();

		Feedback feedback = this.toFeedback(feedbackRequestDto);

		feedback.setCustomer(customer);
		feedback.setOrder(order);
		feedback.setImage(feedbackImage);
		feedback.setDeliveredOrderFeedback(true);
		feedback.setCancelOrderFeedback(false);
		feedback.setReplacementOrderFeedback(false);

		Feedback savedFeedback = this.feedbackRepo.save(feedback);

		return this.toFeedbackResponseDto(savedFeedback);
	}

	@Override
	public FeedbackResponseDto addFeedbackForDeliveredOrderWithImage(String contact, Integer orderId,
			FeedbackRequestDto feedbackRequestDto, MultipartFile image)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException {

		Order order = this.orderRepo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order", "Order Id", orderId));

		Customer customer = this.customerRepo.findByContact(contact)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "Contact Number", contact));

		String url = "http://localhost:8088/bestbuy/products/image/";

		Image feedbackImage;

		ImageResponse imageResponse = this.fileServices.addImage(path, image);

		feedbackImage = new Image();

		feedbackImage.setImageName(imageResponse.getFileName());
		feedbackImage.setImageUrl(url.concat(imageResponse.getFileName()));

		Feedback feedback = this.toFeedback(feedbackRequestDto);

		feedback.setCustomer(customer);
		feedback.setOrder(order);
		feedback.setImage(feedbackImage);
		feedback.setDeliveredOrderFeedback(true);
		feedback.setCancelOrderFeedback(false);
		feedback.setReplacementOrderFeedback(false);

		Feedback savedFeedback = this.feedbackRepo.save(feedback);

		return this.toFeedbackResponseDto(savedFeedback);
	}

	@Override
	public FeedbackResponseDto addFeedbackForCancelledOrderWithImage(String contact, Integer orderId,
			FeedbackRequestDto feedbackRequestDto, MultipartFile image)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException {

		Order order = this.orderRepo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order", "Order Id", orderId));

		Customer customer = this.customerRepo.findByContact(contact)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "Contact Number", contact));

		String url = "http://localhost:8088/bestbuy/products/image/";

		ImageResponse imageResponse = this.fileServices.addImage(path, image);

		Image feedbackImage = new Image();

		feedbackImage.setImageName(imageResponse.getFileName());
		feedbackImage.setImageUrl(url.concat(imageResponse.getFileName()));

		Feedback feedback = this.toFeedback(feedbackRequestDto);

		feedback.setCustomer(customer);
		feedback.setOrder(order);
		feedback.setImage(feedbackImage);
		feedback.setDeliveredOrderFeedback(false);
		feedback.setCancelOrderFeedback(true);
		feedback.setReplacementOrderFeedback(false);

		Feedback savedFeedback = this.feedbackRepo.save(feedback);

		return this.toFeedbackResponseDto(savedFeedback);

	}

	@Override
	public FeedbackResponseDto addFeedbackForCancelledOrder(String contact, Integer orderId,
			FeedbackRequestDto feedbackRequestDto)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException {

		Order order = this.orderRepo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order", "Order Id", orderId));

		Customer customer = this.customerRepo.findByContact(contact)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "Contact Number", contact));

		Image feedbackImage = new Image();

		Feedback feedback = this.toFeedback(feedbackRequestDto);

		feedback.setCustomer(customer);
		feedback.setOrder(order);
		feedback.setImage(feedbackImage);
		feedback.setDeliveredOrderFeedback(false);
		feedback.setCancelOrderFeedback(true);
		feedback.setReplacementOrderFeedback(false);

		Feedback savedFeedback = this.feedbackRepo.save(feedback);

		return this.toFeedbackResponseDto(savedFeedback);

	}

	@Override
	public FeedbackResponseDto addFeedbackForReplacementOrderWithImage(String contact, Integer orderId,

			FeedbackRequestDto feedbackRequestDto, MultipartFile image)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException {

		Order order = this.orderRepo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order", "Order Id", orderId));

		Customer customer = this.customerRepo.findByContact(contact)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "Contact Number", contact));

		String url = "http://localhost:8088/bestbuy/products/image/";

		Image feedbackImage;

		ImageResponse imageResponse = this.fileServices.addImage(path, image);

		feedbackImage = new Image();

		feedbackImage.setImageName(imageResponse.getFileName());
		feedbackImage.setImageUrl(url.concat(imageResponse.getFileName()));

		Feedback feedback = this.toFeedback(feedbackRequestDto);

		feedback.setCustomer(customer);
		feedback.setOrder(order);
		feedback.setImage(feedbackImage);
		feedback.setDeliveredOrderFeedback(false);
		feedback.setCancelOrderFeedback(false);
		feedback.setReplacementOrderFeedback(true);

		Feedback savedFeedback = this.feedbackRepo.save(feedback);

		return this.toFeedbackResponseDto(savedFeedback);

	}

	@Override
	public FeedbackResponseDto addFeedbackForReplacementOrder(String contact, Integer orderId,
			FeedbackRequestDto feedbackRequestDto)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException {

		Order order = this.orderRepo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order", "Order Id", orderId));

		Customer customer = this.customerRepo.findByContact(contact)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "Contact Number", contact));

		Image feedbackImage;

		feedbackImage = new Image();

		Feedback feedback = this.toFeedback(feedbackRequestDto);

		feedback.setCustomer(customer);
		feedback.setOrder(order);
		feedback.setImage(feedbackImage);
		feedback.setDeliveredOrderFeedback(false);
		feedback.setCancelOrderFeedback(false);
		feedback.setReplacementOrderFeedback(true);

		Feedback savedFeedback = this.feedbackRepo.save(feedback);

		return this.toFeedbackResponseDto(savedFeedback);

	}

	@Override
	public Page<Feedback> getFeedbacksByCustomer(String contact, Integer page, Integer size,
			String sortDirection, String sortBy) throws ResourceNotFoundException {

		Customer customer = this.customerRepo.findByContact(contact)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "Contact Number", contact));

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Feedback> pagePost = this.feedbackRepo.findByCustomer(customer, pageable);

		return pagePost;
	}

	@Override
	public Page<Feedback> getFeedbacksByOrder(Integer orderId,Integer page, Integer size,
			String sortDirection, String sortBy)
			throws ResourceNotFoundException {

		Order order = this.orderRepo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order", "Order Id", orderId));
		
		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(page, size, sort);
		
		return this.feedbackRepo.findByOrder(order,pageable);
	}

	@Override
	public Page<Feedback> getFeedbacksByRating(Integer page, Integer size, String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by("rating").ascending()
				: Sort.by("rating").descending();

		Pageable pageable = PageRequest.of(page, size, sort);

		return this.feedbackRepo.findAll(pageable);

	}

	@Override
	public Page<Feedback> getFeedbacksByDate(Integer page, Integer size, String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by("feebackTimeStamp").ascending()
				: Sort.by("feebackTimeStamp").descending();

		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Feedback> pagePost = this.feedbackRepo.findAll(pageable);

		return pagePost;

	}

	// Model Mapper Methods

	private FeedbackResponseDto toFeedbackResponseDto(Feedback feedback) {

		return this.modelMapper.map(feedback, FeedbackResponseDto.class);

	}

	private Feedback toFeedback(FeedbackRequestDto feedbackRequestDto) {

		return this.modelMapper.map(feedbackRequestDto, Feedback.class);

	}

	@Override
	public void serveFeedBackImage(String imageName, HttpServletResponse response)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException {

		InputStream resource = this.fileServices.serveImage(imageName, imageName);

		response.setContentType(MediaType.IMAGE_JPEG_VALUE);

		StreamUtils.copy(resource, response.getOutputStream());
	}

	@Override
	public ApiResponse deleteFeedBackImage(String fileName) throws IOException {

		boolean delete = this.fileServices.delete(fileName);

		if (delete) {
			return new ApiResponse(LocalDateTime.now(), "Image File Deleted Successfully !", true);
		} else {
			return new ApiResponse(LocalDateTime.now(), "Requested Image File Does Not Exist !", false);
		}

	}

	@Override
	public Page<Feedback> getAllFeedbacks(Integer pageNumber, Integer pageSize) {

		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		Page<Feedback> pagePost = this.feedbackRepo.findAll(pageable);

		return pagePost;

	}

	@Override
	public FeedbackResponseDto getFeedbackById(Integer feedbackId) throws ResourceNotFoundException {

		Feedback feedback = this.feedbackRepo.findById(feedbackId)
				.orElseThrow(() -> new ResourceNotFoundException("Feedback", "Feedback Id", feedbackId));

		return this.toFeedbackResponseDto(feedback);

	}

}
