/**
 * 
 */
package com.masai.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class ReturnOrderRequest {

	// Feedback or Reason For Return

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer returnOrderRequestId;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime returnTimeStamp;
	
	@UpdateTimestamp
	@Column(nullable = false)
	private LocalDateTime returnUpdatedTimeStamp;

	
	private Boolean isReturnOrderPickedup;

	private Boolean returnForReplacement;

	private Boolean returnForRefund;

	private String status;

	@OneToOne
	private Order order;

	@OneToOne
	private Customer customer;

	@OneToOne(cascade = CascadeType.ALL)
	private PickUpOrderRequest pickUpOrderRequest;

	@OneToOne(cascade = CascadeType.ALL)
	private ReplaceOrderRequest replaceOrderRequest;

	@OneToOne(cascade = CascadeType.ALL)
	private RefundOrderRequest refundOrderRequest;

}
