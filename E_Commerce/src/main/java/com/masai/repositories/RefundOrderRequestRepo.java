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
import com.masai.model.RefundOrderRequest;

/**
 * @author tejas
 *
 */

@Repository
public interface RefundOrderRequestRepo extends JpaRepository<RefundOrderRequest, Integer> {

	Page<RefundOrderRequest> findByApproved(Boolean approved, Pageable pageable);

	Optional<RefundOrderRequest> findByOrder(Order order);
	
	Page<RefundOrderRequest> findByCustomer(Customer customer,Pageable pageable);
}
