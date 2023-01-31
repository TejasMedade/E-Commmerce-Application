/**
 * 
 */
package com.masai.exceptions;

/**
 * @author tejas
 *
 */
public class ResourceNotAllowedException extends Exception {

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

	//Payment Method Associated With This Payment Id is Temporarily Unavailable !
	
	
	public ResourceNotAllowedException(String resourceName,String fieldName, Integer fieldValue2) {
		super(String.format("%s Associated With This %s : %s is Temporarily Unavailable !", resourceName,fieldName, fieldValue2));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue2 = fieldValue2;
	}
	
	

}
