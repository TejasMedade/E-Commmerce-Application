/**
 * 
 */
package com.masai.exceptions;

/**
 * @author tejas
 *
 */
public class DuplicateResourceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String resourceName;
	String fieldName;
	String fieldValue;

	/**
	 * @param resourceName
	 * @param fieldName
	 * @param userId
	 */
	public DuplicateResourceException(String resourceName, String fieldName, String userId) {
		super(String.format("Error : %s Already Registered With This %s : %s", resourceName, fieldName, userId));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = userId;
	}

}
