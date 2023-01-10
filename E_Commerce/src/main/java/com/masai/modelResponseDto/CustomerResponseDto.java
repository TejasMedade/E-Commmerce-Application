/**
 * 
 */
package com.masai.modelResponseDto;

import java.time.LocalDate;

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
public class CustomerResponseDto {

	private Integer customerId;

	private String firstName;

	private String lastName;

	private String contact;

	private String email;

	private LocalDate dateOfBirth;

	private LocalDate accountCreatedDate;

	private AddressResponseDto address;

}
