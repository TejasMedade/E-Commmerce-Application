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

public class PickUpOrderDetailsResponseDto extends RepresentationModel<PickUpOrderDetailsResponseDto> {

	private Integer pickUpOrderRequestId;

	private LocalDateTime pickUpOrderTimeStamp;

	private LocalDateTime pickUpOrderUpdatedTimeStamp;

	private Boolean isReturnOrderPickedUp;

	private LocalDateTime expectedPickUpDate;

	private CustomerDetailsResponseDto customer;

	private OrderDetailsResponseDto order;

}
