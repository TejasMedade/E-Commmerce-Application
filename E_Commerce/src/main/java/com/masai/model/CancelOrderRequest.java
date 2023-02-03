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
public class CancelOrderRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cancelOrderRequestId;
	
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime cancelTimeStamp;

	private Boolean isOrderCancelled;

	private Boolean isOrderRefunded;

	private String status;

	@OneToOne
	private Order order;

	@OneToOne
	private Customer customer;

	@OneToOne(cascade = CascadeType.ALL)
	private RefundOrderRequest refundOrderRequest;

}
