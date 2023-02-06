/**
 * 
 */
package com.masai.JWT.security.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tejas
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthRequest {
	
	//For Customer & Admin Username is Contact Number
	private String username;

	private String password;

}
