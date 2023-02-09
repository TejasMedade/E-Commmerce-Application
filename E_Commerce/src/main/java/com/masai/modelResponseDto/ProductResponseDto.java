package com.masai.modelResponseDto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.masai.model.Image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto extends RepresentationModel<ProductResponseDto> {

	private Integer productId;

	private String productName;

	private String productDescription;

	private String productTechnicalDetails;

	private String brand;

	private String type;

	private Double salePrice;

	private Double marketPrice;

	private Integer discountPercentage;

	private Integer stockQuantity;

	private Double rating;

	private Boolean available;

	private Boolean onDiscountSale;

	private Boolean buyerschoice;

	private List<Image> images;

	private Integer manufacturingYear;

	private Integer manufacturingMonth;

	private LocalDateTime productAddedDateTime;

	private LocalDateTime productUpdatedDateTime;

	private ProductCategoryResponseDto category;

	private List<ReviewResponseDto> listOfReviews;
	
	private Long totalSales;

}
