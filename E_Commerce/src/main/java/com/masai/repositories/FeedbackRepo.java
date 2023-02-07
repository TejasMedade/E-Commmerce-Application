/**
 * 
 */
package com.masai.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.Customer;
import com.masai.model.Feedback;
import com.masai.model.Order;

/**
 * @author tejas
 *
 */
@Repository
public interface FeedbackRepo extends JpaRepository<Feedback, Integer> {

	Page<Feedback> findByCustomer(Customer customer, Pageable pageable);

	Page<Feedback> findByOrder(Order order, Pageable pageable);

}
