package com.masai.modelResponseDto;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {

	private Integer productId;

	private String productName;

	private String productDescription;

	private String brand;

	private String type;

	private Double salePrice;

	private Double marketPrice;

	private Integer discountPercentage;

	private Integer stockQuantity;

	private Integer rating;

	private Boolean available;

	private Boolean onDiscountSale;

	private Boolean buyerschoice;

	private List<String> images;

	private YearMonth manufacturingMonthYear;

	private LocalDate productAddedDate;

	private CategoryResponseDto categoryResponseDto;
}
