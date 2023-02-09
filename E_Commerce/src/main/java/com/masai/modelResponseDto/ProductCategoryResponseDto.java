/**
 * 
 */
package com.masai.modelResponseDto;

import java.time.LocalDateTime;

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
public class ProductCategoryResponseDto {

	private Integer categoryId;

	private String categoryName;

	private String categoryDescription;

	private Boolean active;
	
	private LocalDateTime categoryAddedDateTime;
	
	private LocalDateTime categoryUpdatedDateTime;
}
