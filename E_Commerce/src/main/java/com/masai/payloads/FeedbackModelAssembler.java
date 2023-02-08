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

import com.masai.controllers.FeedbackController;
import com.masai.exceptions.ResourceNotAllowedException;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.model.Feedback;
import com.masai.modelResponseDto.FeedbackResponseDto;

/**
 * @author tejas
 *
 */
@Component
public class FeedbackModelAssembler extends RepresentationModelAssemblerSupport<Feedback, FeedbackResponseDto> {

	@Autowired
	private ModelMapper modelMapper;

	public FeedbackModelAssembler() {

		super(FeedbackController.class, FeedbackResponseDto.class);

	}

	@Override
	public FeedbackResponseDto toModel(Feedback entity) {

		FeedbackResponseDto feedbackResponseDto = this.modelMapper.map(entity, FeedbackResponseDto.class);

		try {
			try {
				feedbackResponseDto
						.add(linkTo(methodOn(FeedbackController.class).getFeedbackById(feedbackResponseDto.getFeedbackId()))
								.withSelfRel());
			} catch (ResourceNotAllowedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}

		return feedbackResponseDto;
	}

}
