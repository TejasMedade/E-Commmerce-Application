/**
 * 
 */
package com.masai.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer addressId;

	private String streetNo;

	private String buildingName;

	private String city;

	private String state;

	private String country;

	private String pincode;

}
