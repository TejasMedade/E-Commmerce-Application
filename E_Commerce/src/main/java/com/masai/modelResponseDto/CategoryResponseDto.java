/**
 * 
 */
package com.masai.modelResponseDto;

import java.util.List;
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
public class CategoryResponseDto {

	private Integer categoryId;

	private String categoryName;

	private String subCategory;

	private String categoryDescription;

	private Boolean active;

	private List<ProductResponseDto> listOfProducts;
}
