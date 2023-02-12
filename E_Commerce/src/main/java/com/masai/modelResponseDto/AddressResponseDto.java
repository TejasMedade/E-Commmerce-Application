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
public class AddressResponseDto extends RepresentationModel<AddressResponseDto> {

	private Integer addressId;

	private String addressLine1;

	private String addressLine2;

	private String buildingName;

	private String landMark;

	private String city;

	private String state;

	private String country;

	private String pincode;

	private LocalDateTime addressCreatedDate;

	private LocalDateTime addressUpdatedDate;
}
