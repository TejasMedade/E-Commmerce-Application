/**
 * 
 */
package com.masai.payloads;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.masai.controllers.ReviewController;
import com.masai.exceptions.ResourceNotAllowedException;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.model.Review;
import com.masai.modelResponseDto.ReviewResponseDto;

/**
 * @author tejas
 *
 */
@Component
public class ReviewModelAssembler extends RepresentationModelAssemblerSupport<Review, ReviewResponseDto> {

	@Autowired
	private ModelMapper modelMapper;

	public ReviewModelAssembler() {

		super(ReviewController.class, ReviewResponseDto.class);

	}

	@Override
	public ReviewResponseDto toModel(Review entity) {

		ReviewResponseDto reviewResponseDto = this.modelMapper.map(entity, ReviewResponseDto.class);

		try {
			try {
				reviewResponseDto
						.add(linkTo(methodOn(ReviewController.class).getReviewById(reviewResponseDto.getReviewId()))
								.withSelfRel());
			} catch (ResourceNotAllowedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}

		return reviewResponseDto;
	}

}
