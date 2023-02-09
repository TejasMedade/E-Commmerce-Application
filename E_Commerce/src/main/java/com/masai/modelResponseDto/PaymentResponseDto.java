/**
 * 
 */
package com.masai.modelResponseDto;

import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

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
public class PaymentResponseDto extends RepresentationModel<PaymentResponseDto> {

	private Integer paymentId;

	private String paymentType;

	private Boolean allowed;

	private LocalDateTime paymentAddedTimeStamp;

	private LocalDateTime paymentUpdatedTimeStamp;

}
