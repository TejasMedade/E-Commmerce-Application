/**
 * 
 */
package com.masai.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.Cart;
import com.masai.model.Customer;

/**
 * @author tejas
 *
 */

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {

	public Optional<Cart> findByCustomer(Customer customer);

}
