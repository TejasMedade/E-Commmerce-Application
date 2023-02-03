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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tejas
 *
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ReplaceOrderRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer replaceOrderRequestId;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime replaceTimeStamp;
	
	@UpdateTimestamp
	@Column(nullable = false)
	private LocalDateTime replaceUpdatedTimeStamp;
		
	@ManyToOne
	private Customer customer;

	@OneToOne
	private Order order;

	private String status;
	
	private String approvedBy;

	private Boolean approved;
	
	// Replacement Order
	private Boolean isReplacementOrderGenerated;

	// Replacement Order for Original Order
	private Integer replacemenetOrderId;
	
	
}
