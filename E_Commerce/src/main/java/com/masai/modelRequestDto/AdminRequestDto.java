/**
 * 
 */
package com.masai.modelRequestDto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
public class AdminRequestDto {
	
	
	@NotNull(message = "{Admin.name.invalid}")
	@NotBlank(message = "{Admin.name.invalid}")
	@NotEmpty(message = "{Admin.name.invalid}")
	private String firstName;
	
	@NotNull(message = "{Admin.name.invalid}")
	@NotBlank(message = "{Admin.name.invalid}")
	@NotEmpty(message = "{Admin.name.invalid}")
	private String lastName;
	
	@NotNull(message = "{Admin.contact.invalid}")
	@NotBlank(message = "{Admin.contact.invalid}")
	@NotEmpty(message = "{Admin.contact.invalid}")
	@Size(min = 10, max = 12, message = "{Admin.contact.invalid}")
	private String contact;
	
	@NotNull(message = "{Admin.email.invalid}")
	@NotBlank(message = "{Admin.email.invalid}")
	@NotEmpty(message = "{Admin.email.invalid}")
	@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE, message = "{Admin.email.invalid}")
	private String email;

	@NotNull(message = "{Admin.password.invalid}")
	@NotBlank(message = "{Admin.password.invalid}")
	@NotEmpty(message = "{Admin.password.invalid}")
	@Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){6,12}$", message = "{Admin.password.invalid}")
	private String password;
	
	
}
