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
public class RefundOrderDetailsResponseDto extends RepresentationModel<RefundOrderDetailsResponseDto> {

	private Integer refundOrderRequestId;

	private LocalDateTime refundTimestamp;

	private LocalDateTime refundUpdatedTimeStamp;

	private Double orderTotalAmount;

	private String approvedBy;

	private Boolean approved;

	private String status;

	private PaymentResponseDto payment;

	private CustomerDetailsResponseDto customer;

	private OrderDetailsResponseDto order;

}
