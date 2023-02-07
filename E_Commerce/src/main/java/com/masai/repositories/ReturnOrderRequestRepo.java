/**
 * 
 */
package com.masai.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.Order;
import com.masai.model.RefundOrderRequest;
import com.masai.model.ReplaceOrderRequest;
import com.masai.model.ReturnOrderRequest;

/**
 * @author tejas
 *
 */
@Repository
public interface ReturnOrderRequestRepo extends JpaRepository<ReturnOrderRequest, Integer> {

	Optional<ReturnOrderRequest> findByOrder(Order order);
	
	Optional<ReturnOrderRequest> findByReplaceOrderRequest(ReplaceOrderRequest replaceOrderRequest);
	
	Optional<ReturnOrderRequest> findByRefundOrderRequest(RefundOrderRequest refundOrderRequest);
}
