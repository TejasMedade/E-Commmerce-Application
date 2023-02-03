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
public class RefundOrderRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer refundOrderRequestId;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime refundTimeStamp;
	
	@UpdateTimestamp
	@Column(nullable = false)
	private LocalDateTime refundUpdatedTimeStamp;
	
	private Double orderTotalAmount;

	private String approvedBy;

	private Boolean approved;

	private String status;

	@OneToOne
	private Payment payment;

	@ManyToOne
	private Customer customer;

	@OneToOne
	private Order order;
}
