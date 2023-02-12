package com.masai.modelResponseDto;

import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplaceOrderDetailsResponseDto extends RepresentationModel<ReplaceOrderDetailsResponseDto> {

	private Integer replaceOrderRequestId;

	private LocalDateTime replaceTimeStamp;

	private String status;
	
	private String approvedBy;

	private Boolean approved;

	private CustomerDetailsResponseDto customer;
	
	private OrderDetailsResponseDto order;
	
	// Replacement Order
	private Boolean isReplacementOrderGenerated;

	// Replacement Order for Original Order
	private Integer replacemenetOrderId;
	
}
