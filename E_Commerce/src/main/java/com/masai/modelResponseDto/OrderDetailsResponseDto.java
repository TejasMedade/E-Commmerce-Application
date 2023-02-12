/**
 * 
 */
package com.masai.modelResponseDto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.masai.model.OrderProductDetails;

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
public class OrderDetailsResponseDto extends RepresentationModel<OrderDetailsResponseDto> {

	private Integer orderId;

	private String status;

	private LocalDateTime orderTimeStamp;
	
	private LocalDateTime orderUpdatedTimeStamp;

	private LocalDateTime expectedDeliveryDate;

	private Integer orderQuantity;

	private Double orderTotalAmount;

	private List<OrderProductDetails> listOfProducts;

}
