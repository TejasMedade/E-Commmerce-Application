/**
 * 
 */
package com.masai.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.CancelOrderRequest;
import com.masai.model.Order;

/**
 * @author tejas
 *
 */
@Repository
public interface CancelOrderRequestRepo extends JpaRepository<CancelOrderRequest, Integer> {
	
	Optional<CancelOrderRequest> findByOrder(Order order);
	
	
}
