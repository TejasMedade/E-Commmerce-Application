/**
 * 
 */
package com.masai.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ASUS
 *
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

	@Id
	private Integer paymentId;

	private Boolean paymentType;

	private Boolean allowed;

}
