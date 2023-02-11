/**
 * 
 */
package com.masai.modelRequestDto;

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
public class PaymentRequestDto {
	
	@NotNull(message = "{Payment.id.invalid}")
	private Integer paymentId;
	
	@NotNull(message = "{Payment.type.invalid}")
	@NotBlank(message = "{Payment.type.invalid}")
	@NotEmpty(message = "{Payment.type.invalid}")
	private String paymentType;
	
	@NotNull(message = "{Payment.allowed.invalid}")
	private Boolean allowed;

}
