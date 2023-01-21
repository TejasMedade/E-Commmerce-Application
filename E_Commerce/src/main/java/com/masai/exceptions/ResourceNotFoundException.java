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
	Integer fieldValue2;

	/**
	 * @param resourceName
	 * @param fieldName
	 * @param userId
	 */

	public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
		super(String.format("%s Not Found With This %s : %s", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public ResourceNotFoundException(String resourceName, String fieldName, Integer fieldValue2) {
		super(String.format("%s Not Found With This %s : %s", resourceName, fieldName, fieldValue2));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue2 = fieldValue2;
	}
	
	public ResourceNotFoundException( String fieldName) {
		super(String.format("%s is Empty !", fieldName));
		this.fieldName = fieldName;
	}
	
}
