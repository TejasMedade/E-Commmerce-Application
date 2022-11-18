/**
 * 
 */
package com.masai.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tejas
 *
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentCustomerSession {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer currentSessionId;

	private Integer customerId;

	private String key;

	private LocalDateTime localDateTime;

}
