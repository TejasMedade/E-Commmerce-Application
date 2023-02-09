/**
 * 
 */
package com.masai.modelResponseDto;

import java.time.LocalDateTime;

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
public class ReplaceOrderResponseDto {

	private Integer replaceOrderRequestId;

	private LocalDateTime replaceTimeStamp;

	private LocalDateTime replaceUpdatedTimeStamp;

	private String status;

	private Boolean approved;

	// Replacement Order
	private Boolean isReplacementOrderGenerated;

	// Replacement Order for Original Order
	private Integer replacemenetOrderId;

}
