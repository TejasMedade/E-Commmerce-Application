/**
 * 
 */
package com.masai.repository;

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

	public Optional<Customer> findByMobileNumber(String mobileNumber);

}
