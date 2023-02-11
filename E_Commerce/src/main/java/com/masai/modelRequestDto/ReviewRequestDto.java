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
public class ReviewRequestDto {

	@NotNull(message = "{Review.field.invalid}")
	@NotBlank(message = "{Review.field.invalid}")
	@NotEmpty(message = "{Review.field.invalid}")
	private String customerReview;

	@Max(5)
	@NotNull(message = "{Review.rating.invalid}")
	private Double customerRating;

}
