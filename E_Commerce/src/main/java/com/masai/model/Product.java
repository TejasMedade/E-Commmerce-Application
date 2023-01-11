/**
 * 
 */
package com.masai.model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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

	// Images

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	private YearMonth manufacturingMonthYear;

	private LocalDate productAddedDate;

	@ManyToOne
	private Category category;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Review> listOfReviews = new ArrayList<>();

}
