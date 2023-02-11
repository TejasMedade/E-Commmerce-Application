package com.masai.modelRequestDto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {

	@NotNull(message = "{Product.name.invalid}")
	@NotBlank(message = "{Product.name.invalid}")
	@NotEmpty(message = "{Product.name.invalid}")
	private String productName;

	@NotNull(message = "{Product.description.invalid}")
	@NotBlank(message = "{Product.description.invalid}")
	@NotEmpty(message = "{Product.description.invalid}")
	private String productDescription;

	@NotNull(message = "{Product.technicalDetails.invalid}")
	@NotBlank(message = "{Product.technicalDetails.invalid}")
	@NotEmpty(message = "{Product.technicalDetails.invalid}")
	private String productTechnicalDetails;

	@NotNull(message = "{Product.brand.invalid}")
	@NotBlank(message = "{Product.brand.invalid}")
	@NotEmpty(message = "{Product.brand.invalid}")
	private String brand;

	@NotNull(message = "{Product.type.invalid}")
	@NotBlank(message = "{Product.type.invalid}")
	@NotEmpty(message = "{Product.type.invalid}")
	private String type;

	@NotNull(message = "{Product.marketPrice.invalid}")
	private Double marketPrice;

	@NotNull(message = "{Product.discountPercentage.invalid}")
	private Integer discountPercentage;

	@NotNull(message = "{Product.stockQuantity.invalid}")
	private Integer stockQuantity;

	@Max(5)
	@NotNull(message = "{Product.rating.invalid}")
	private Double rating;

	@NotNull(message = "{Product.manufacturingYear.invalid}")
	private Integer manufacturingYear;

	@NotNull(message = "{Product.manufacturingMonth.invalid}")
	private Integer manufacturingMonth;

}
