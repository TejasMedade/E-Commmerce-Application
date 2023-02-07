/**
 * 
 */
package com.masai.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.Customer;
import com.masai.model.Order;
import com.masai.model.ReplaceOrderRequest;

/**
 * @author tejas
 *
 */
@Repository
public interface ReplaceOrderRequestRepo extends JpaRepository<ReplaceOrderRequest, Integer> {

	Page<ReplaceOrderRequest> findByApproved(Boolean approved, Pageable pageable);
	
	Optional<ReplaceOrderRequest> findByOrder(Order order);
	
	Page<ReplaceOrderRequest> findByCustomer(Customer customer, Pageable pageable);
}
