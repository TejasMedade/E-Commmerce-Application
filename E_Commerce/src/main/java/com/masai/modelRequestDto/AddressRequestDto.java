/**
 * 
 */
package com.masai.modelRequestDto;

import javax.persistence.Column;
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
public class AddressRequestDto {
	
	@NotNull(message = "{Address.addressLine1.invalid}")
	@NotBlank(message = "{Address.addressLine1.invalid}")
	@NotEmpty(message = "{Address.addressLine1.invalid}")
	@Column(length = Integer.MAX_VALUE)
	private String addressLine1;
	
	@NotNull(message = "{Address.addressLine2.invalid}")
	@NotBlank(message = "{Address.addressLine2.invalid}")
	@NotEmpty(message = "{Address.addressLine2.invalid}")
	@Column(length = Integer.MAX_VALUE)
	private String addressLine2;
	
	@NotNull(message = "{Address.buildingname.invalid}")
	@NotBlank(message = "{Address.buildingname.invalid}")
	@NotEmpty(message = "{Address.buildingname.invalid}")
	private String buildingName;
	
	@NotNull(message = "{Address.landmark.invalid}")
	@NotBlank(message = "{Address.landmark.invalid}")
	@NotEmpty(message = "{Address.landmark.invalid}")
	private String landMark;
	
	@NotNull(message = "{Address.city.invalid}")
	@NotBlank(message = "{Address.city.invalid}")
	@NotEmpty(message = "{Address.city.invalid}")
	private String city;
	
	@NotNull(message = "{Address.state.invalid}")
	@NotBlank(message = "{Address.state.invalid}")
	@NotEmpty(message = "{Address.state.invalid}")
	private String state;
	
	@NotNull(message = "{Address.country.invalid}")
	@NotBlank(message = "{Address.country.invalid}")
	@NotEmpty(message = "{Address.country.invalid}")
	private String country;
	
	@NotNull(message = "{Address.pincode.invalid}")
	@NotBlank(message = "{Address.pincode.invalid}")
	@NotEmpty(message = "{Address.pincode.invalid}")
	private String pincode;
	
}
