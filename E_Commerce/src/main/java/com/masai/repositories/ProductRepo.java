/**
 * 
 */
package com.masai.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.Category;
import com.masai.model.Product;

/**
 * @author tejas
 *
 */
@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

	List<Product> findByproductNameContaining(String keyword);

	List<Product> findByTypeContaining(String keyword);

	List<Product> findByCategory(Category category);

	Page<Product> findByCategoryOrderByRating(Category category, Pageable pageable);

	Page<Product> findByCategoryOrderByRatingDesc(Category category, Pageable pageable);

	Page<Product> findByCategoryOrderByProductAddedDateTime(Category category, Pageable pageable);

	Page<Product> findByCategoryOrderByProductAddedDateTimeDesc(Category category, Pageable pageable);

	Page<Product> findByBuyerschoice(Boolean buyerschoice, Pageable pageable);

	Page<Product> findByDiscountPercentage(Integer discountPercentage, Pageable pageable);

	Page<Product> findByOnDiscountSale(Boolean onDiscountSale, Pageable pageable);

	// Least sold product

	Optional<Product> findFirstByOrderByTotalSalesAsc();

	// Highest sold product

	Optional<Product> findFirstByOrderByTotalSalesDesc();

	// highest sold product ordered by rating in given duration

	Page<Product> findByRatingOrderByTotalSalesAsc(Double rating, Pageable pageable);

	Page<Product> findByRatingOrderByTotalSalesDesc(Double rating, Pageable pageable);

	// highest sold product categorised under selling price

	Page<Product> findBySalePriceOrderByTotalSalesAsc(Double salePrice, Pageable pageable);

	Page<Product> findBySalePriceOrderByTotalSalesDesc(Double salePrice, Pageable pageable);

	// highest sold product in different categories

	// Using JPQL

	// highest sold product ordered by rating in given duration, how to use duration
	// ?

	// Using JPQL
}
