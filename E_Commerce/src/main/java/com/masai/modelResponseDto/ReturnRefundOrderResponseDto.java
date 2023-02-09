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
public class ReturnRefundOrderResponseDto {

	private Integer returnOrderRequestId;

	private Boolean returnForRefund;

	private LocalDateTime returnTimeStamp;
	
	private LocalDateTime returnUpdatedTimeStamp;

	private Boolean isReturnOrderPickedup;

	private String status;

	private OrderDetailsResponseDto order;
	
	private CustomerResponseDto customer;

	private RefundOrderResponseDto refundOrderRequest;

	private PickUpOrderResponseDto pickUpOrderRequest;
}
