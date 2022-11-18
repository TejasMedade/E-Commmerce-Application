/**
 * 
 */
package com.masai.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tejas
 *
 */

@Entity
@Data
@NoArgsConstructor
public class User {

	// Id should be Contact Number
	@Id
	private String id;
	private String password;

	// Choose From Admin or Customer
	private String role;
}
