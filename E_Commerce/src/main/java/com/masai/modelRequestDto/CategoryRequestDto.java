/**
 * 
 */
package com.masai.modelRequestDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
public class CategoryRequestDto {
	
	@NotEmpty(message = "{Category.name.invalid}")
	@NotNull(message = "{Category.name.invalid}")
	@NotBlank(message = "{Category.name.invalid}")
	@Size(min = 3, message = "Category Name Should be Minimum of 3 Characters")
	private String categoryName;
	
	@NotEmpty(message = "{Category.description.invalid}")
	@NotNull(message = "{Category.description.invalid}")
	@NotBlank(message = "{Category.description.invalid}")
	private String categoryDescription;
	
	@NotNull(message = "Category Should be Active or InActive")
	private Boolean active;

}
