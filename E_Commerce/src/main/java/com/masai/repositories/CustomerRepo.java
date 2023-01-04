/**
 * 
 */
package com.masai.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.Customer;

/**
 * @author tejas
 *
 */

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {

	Optional<Customer> findByContact(String contact);
	

	
}
