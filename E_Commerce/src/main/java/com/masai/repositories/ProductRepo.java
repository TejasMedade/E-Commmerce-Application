/**
 * 
 */
package com.masai.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.Category;
import com.masai.model.Product;

/**
 * @author tejas
 *
 */
@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

	List<Product> findByproductNameContaining(String keyword);
	
	List<Product> findByCategory(Category category);

}
