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
	Integer fieldValue2;

	/**
	 * @param resourceName
	 * @param fieldName
	 * @param userId
	 */
	public DuplicateResourceException(String resourceName, String fieldName, String fieldValue) {
		super(String.format("Error : %s Already Registered With This %s : %s", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	/**
	 * @param resourceName
	 * @param fieldName
	 * @param fieldValue2
	 */
	public DuplicateResourceException(String resourceName, String fieldName, Integer fieldValue2) {
		super(String.format("Error : Another %s Method is Already Registered With This %s : %s", resourceName, fieldName, fieldValue2));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue2 = fieldValue2;
	}
	
	

}
