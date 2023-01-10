package com.masai.modelRequestDto;

import java.time.LocalDate;
import java.time.YearMonth;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

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
	@Column(length = Integer.MAX_VALUE)
	private String productDescription;

	@NotNull(message = "{Product.brand.invalid}")
	@NotBlank(message = "{Product.brand.invalid}")
	@NotEmpty(message = "{Product.brand.invalid}")
	private String brand;

	@NotNull(message = "{Product.type.invalid}")
	@NotBlank(message = "{Product.type.invalid}")
	@NotEmpty(message = "{Product.type.invalid}")
	private String type;

	@NotNull(message = "{Product.salePrice.invalid}")
	private Double salePrice;

	@NotNull(message = "{Product.marketPrice.invalid}")
	private Double marketPrice;

	@NotNull(message = "{Product.discountPercentage.invalid}")
	private Integer discountPercentage;

	@NotNull(message = "{Product.stockQuantity.invalid}")
	private Integer stockQuantity;

	@Max(5)
	@NotNull(message = "{Product.rating.invalid}")
	private Integer rating;

	@NotNull(message = "{Product Availability Should be True or False}")
	private Boolean available;

	@NotNull(message = "{Product On Sale or Discount Should be True or False}")
	private Boolean onDiscountSale;

	@NotNull(message = "{Customer's Best Choice Should be True or False}")
	private Boolean buyerschoice;

	@NotNull
	@PastOrPresent(message = "Year of Manufacturating Should be in Past")
	private YearMonth manufacturingMonthYear;

	@NotNull
	@PastOrPresent(message = "Product Added Date Should be in Past")
	private LocalDate productAddedDate;

}
