/**
 * 
 */
package com.masai.JWT.security.response;



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
public class JwtAuthResponse {

	// Token will be send through cookie
	// If you dont want it from JSON Body, Delete this field
	private String token;

	private Object response;

}
