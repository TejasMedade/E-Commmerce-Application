/**
 * 
 */
package com.masai.modelResponseDto;

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
public class AddressResponseDto {

	private Integer addressId;

	private String addressLine1;

	private String addressLine2;

	private String landMark;

	private String city;

	private String state;

	private String country;

	private String pincode;

}
