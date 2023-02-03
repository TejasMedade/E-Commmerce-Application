/**
 * 
 */
package com.masai.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
	@Column(unique = true)
	private Integer paymentId;

	private String paymentType;

	private Boolean allowed;
	
	@CreationTimestamp
	@Column(nullable = false,updatable = false)
	private LocalDateTime paymentAddedTimeStamp;
	
	@UpdateTimestamp
	@Column(nullable = false)
	private LocalDateTime paymentUpdatedTimeStamp;
}
