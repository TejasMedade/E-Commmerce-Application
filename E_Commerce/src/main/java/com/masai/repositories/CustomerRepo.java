/**
 * 
 */
package com.masai.repositories;

import java.util.List;
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

	List<Customer> findByEmail(String email);

	List<Customer> findByFirstName(String firstName);

	List<Customer> findByLastName(String lastName);

	Boolean existsByEmail(String email);

	Boolean existsByContact(String contact);

	List<Customer> findByFirstNameAndLastName(String firstName, String lastName);
}
