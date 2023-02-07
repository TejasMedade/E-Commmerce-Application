/**
 * 
 */
package com.masai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.Role;

/**
 * @author tejas
 *
 */
@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {

}
