/**
 * 
 */
package com.masai.modelRequestDto;

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
public class CustomerRequestDto{

	
	private String firstName;
	
	private String lastName;
	
	private String contact;

	private String email;
	
	private String password;
	
	private LocalDate dateOfBirth;
		
	private AddressRequestDto addressRequestDto;
	
}
