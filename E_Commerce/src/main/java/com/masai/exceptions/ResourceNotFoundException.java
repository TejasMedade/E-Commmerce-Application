/**
 * 
 */
package com.masai.exceptions;

/**
 * @author tejas
 *
 */
public class ResourceNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	String resourceName;
	String fieldName;
	String fieldValue;

	/**
	 * @param resourceName
	 * @param fieldName
	 * @param userId
	 */

	public ResourceNotFoundException(String resourceName, String fieldName, String userId) {
		super(String.format("%s Not Found With This %s : %s", resourceName, fieldName, userId));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = userId;
	}

}
