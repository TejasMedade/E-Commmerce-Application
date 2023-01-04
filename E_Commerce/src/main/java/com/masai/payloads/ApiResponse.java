/**
 * 
 */
package com.masai.payloads;

/**
 * @author tejas
 *
 */
/**
 * 
 */


import java.time.LocalDateTime;

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
public class ApiResponse {

	private LocalDateTime timestamp;

	private String message;

	private boolean status;

}
