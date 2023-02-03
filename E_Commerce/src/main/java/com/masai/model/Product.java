/**
 * 
 */
package com.masai.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tejas
 *
 */

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productId;

	private String productName;

	@Lob
	private String productDescription;

	@Lob
	private String productTechnicalDetails;

	private String brand;

	private String type;

	@NotNull(message = "{Product.salePrice.invalid}")
	private Double salePrice;

	private Double marketPrice;

	private Integer discountPercentage;

	private Integer stockQuantity;

	private Double rating;

	@NotNull(message = "{Product Availability Should be True or False}")
	private Boolean available;

	@NotNull(message = "{Product On Sale or Discount Should be True or False}")
	private Boolean onDiscountSale;

	@NotNull(message = "{Customer's Best Choice Should be True or False}")
	private Boolean buyerschoice;

	private Integer manufacturingYear;

	private Integer manufacturingMonth;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime productAddedDateTime;

	@UpdateTimestamp
	@Column(nullable = false)
	private LocalDateTime productUpdatedDateTime;

	@ManyToOne
	private Category category;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Review> listOfReviews = new ArrayList<>();
		
	
	@ElementCollection
	private List<Image> images;

	private Long totalSales;
}
