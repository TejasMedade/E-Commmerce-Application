/**
 * 
 */
package com.masai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.Cart;

/**
 * @author tejas
 *
 */
@Repository
public interface CartRepo extends JpaRepository<Cart, Integer>{
	
	
	
}
