/**
 * 
 */
package com.masai.servicesImplementation;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.masai.exceptions.FileTypeNotValidException;
import com.masai.exceptions.ResourceNotAllowedException;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.model.Customer;
import com.masai.model.Image;
import com.masai.model.Product;
import com.masai.model.Review;
import com.masai.modelRequestDto.ReviewRequestDto;
import com.masai.modelResponseDto.ReviewResponseDto;
import com.masai.payloads.ApiResponse;
import com.masai.payloads.ImageResponse;
import com.masai.repositories.CustomerRepo;
import com.masai.repositories.ProductRepo;
import com.masai.repositories.ReviewRepo;
import com.masai.services.FileServices;
import com.masai.services.ReviewServices;

/**
 * @author tejas
 *
 */
@Service
public class ReviewServicesImplementation implements ReviewServices {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ReviewRepo reviewRepo;

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private FileServices fileServices;

	@Value("${project.image}")
	private String path;

	@Override
	public ReviewResponseDto addReview(String contact, Integer productId, ReviewRequestDto reviewRequestDto)
			throws ResourceNotFoundException {

		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", productId));

		Customer customer = this.customerRepo.findByContact(contact)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "Contact Number", contact));

		Review review = this.toReview(reviewRequestDto);

		review.setProduct(product);
		review.setCustomer(customer);

		Review savedReview = this.reviewRepo.save(review);

		return this.toReviewResponseDto(savedReview);
	}

	@Override
	public ReviewResponseDto addReviewWithImages(String contact, Integer productId, ReviewRequestDto reviewRequestDto,
			MultipartFile[] images)
			throws ResourceNotFoundException, ResourceNotAllowedException, IOException, FileTypeNotValidException {

		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", productId));

		Customer customer = this.customerRepo.findByContact(contact)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "Contact Number", contact));

		Review review = this.toReview(reviewRequestDto);

		review.setProduct(product);
		review.setCustomer(customer);

		// Adding Images to Review

		List<MultipartFile> list = Arrays.asList(images);

		List<Image> listOfReviewImages = new ArrayList<>();

		for (MultipartFile file : list) {

			ImageResponse imageResponse = this.fileServices.addImage(path, file);

			String url = "http://localhost:8088/bestbuy/reviews/image/";

			Image image = new Image();

			image.setImageName(imageResponse.getFileName());
			image.setImageUrl(url.concat(imageResponse.getFileName()));

			listOfReviewImages.add(image);
		}

		review.setImages(listOfReviewImages);

		Review savedReview = this.reviewRepo.save(review);

		return this.toReviewResponseDto(savedReview);
	}

	@Override
	public List<ReviewResponseDto> getReviewsByProduct(Integer productId) throws ResourceNotFoundException {

		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", productId));

		List<Review> findByProduct = this.reviewRepo.findByProduct(product);

		return findByProduct.stream().map(this::toReviewResponseDto).collect(Collectors.toList());

	}

	@Override
	public List<ReviewResponseDto> getReviewsByCustomer(String contact) throws ResourceNotFoundException {

		Customer customer = this.customerRepo.findByContact(contact)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "Contact Number", contact));

		List<Review> findByCustomer = this.reviewRepo.findByCustomer(customer);

		return findByCustomer.stream().map(this::toReviewResponseDto).collect(Collectors.toList());
	}

	@Override
	public Page<Review> getAllReviewsByProductOrderedByRating(Integer productId, Integer page, Integer size,
			String sortDirection) throws ResourceNotFoundException {

		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", productId));

		Pageable pageable = PageRequest.of(page, size);

		return sortDirection.equalsIgnoreCase("asc")
				? this.reviewRepo.findByProductOrderByCustomerRating(product, pageable)
				: this.reviewRepo.findByProductOrderByCustomerRating(product, pageable);

	}

	@Override
	public Page<Review> getAllReviewsByProductOrderedByDate(Integer productId, Integer page, Integer size)
			throws ResourceNotFoundException {

		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", productId));

		Pageable pageable = PageRequest.of(page, size);

		return this.reviewRepo.findByProductOrderByReviewTimeStamp(product, pageable);

	}

	@Override
	public Page<Review> getAllReviewsSortedByRating(Integer page, Integer size, String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by("customerRating").ascending()
				: Sort.by("customerRating").descending();

		Pageable pageable = PageRequest.of(page, size, sort);

		return this.reviewRepo.findAll(pageable);

	}

	@Override
	public void serveReviewImage(String imageName, HttpServletResponse response)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException {

		InputStream resource = this.fileServices.serveImage(path, imageName);

		response.setContentType(MediaType.IMAGE_JPEG_VALUE);

		StreamUtils.copy(resource, response.getOutputStream());

	}

	@Override
	public ApiResponse deleteReview(Integer reviewId) throws ResourceNotFoundException, IOException {

		Review review = this.reviewRepo.findById(reviewId)
				.orElseThrow(() -> new ResourceNotFoundException("Review", "Review Id", reviewId));

		List<Image> images = review.getImages();

		for (Image image : images) {

			this.fileServices.delete(image.getImageName());

		}

		this.reviewRepo.delete(review);

		return new ApiResponse(LocalDateTime.now(), "Review Deleted Successfully !", true, review);
	}

	// Model Mapper Methods

	private Review toReview(ReviewRequestDto reviewRequestDto) {

		return this.modelMapper.map(reviewRequestDto, Review.class);
	}

	private ReviewResponseDto toReviewResponseDto(Review review) {

		return this.modelMapper.map(review, ReviewResponseDto.class);
	}

	@Override
	public ReviewResponseDto getReviewById(Integer reviewId) throws ResourceNotFoundException {

		Review review = this.reviewRepo.findById(reviewId)
				.orElseThrow(() -> new ResourceNotFoundException("Review", "Review Id", reviewId));

		return this.toReviewResponseDto(review);
	}

	@Override
	public Page<Review> getAllReviews(Integer page, Integer size) {

		Pageable pageable = PageRequest.of(page, size);

		return this.reviewRepo.findAll(pageable);
	}

}
