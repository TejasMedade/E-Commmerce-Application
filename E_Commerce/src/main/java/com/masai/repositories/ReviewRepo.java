/**
 * 
 */
package com.masai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.Customer;
import com.masai.model.Product;
import com.masai.model.Review;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author tejas
 *
 */
@Repository
public interface ReviewRepo extends JpaRepository<Review, Integer> {

	Page<Review> findByProductOrderByCustomerRating(Product product, Pageable pageable);

	Page<Review> findByProductOrderByReviewTimeStamp(Product product, Pageable pageable);
	
	List<Review> findByProduct(Product product);

	List<Review> findByCustomer(Customer customer);
}
