/**
 * 
 */
package com.masai.modelRequestDto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tejas
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequestDto {
	

	@NotNull(message = "{Feedback.field.invalid}")
	@NotBlank(message = "{Feedback.field.invalid}")
	@NotEmpty(message = "{Feedback.field.invalid}")
	private String customerFeedback;
	
	

	@Max(5)
	@NotNull(message = "{Feedback.rating.invalid}")
	private Double rating;
	
}
