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

	private Object responseObject;

	/**
	 * @param timestamp
	 * @param message
	 * @param status
	 */
	public ApiResponse(LocalDateTime timestamp, String message, boolean status) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.status = status;
	}

	public ApiResponse(LocalDateTime now, boolean status, Object responseObject) {
		// TODO Auto-generated constructor stub
	}

}
