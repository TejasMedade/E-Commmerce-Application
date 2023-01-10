package com.masai.modelRequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressUpdateRequestDto {

	private String addressLine1;

	private String addressLine2;

	private String buildingName;

	private String landMark;

	private String city;

	private String state;

	private String country;

	private String pincode;

}
