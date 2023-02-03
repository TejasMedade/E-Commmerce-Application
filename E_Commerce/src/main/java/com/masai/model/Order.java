/**
 * 
 */
package com.masai.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
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
 * @author ASUS
 *
 */

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Order  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;

	// Ordered //Dispatched //Shipped //Delivered //Cancelled //
	private String orderStatus;
	
	@CreationTimestamp
	@Column(nullable = false, updatable = false)	
	private LocalDateTime orderTimeStamp;
	
	@UpdateTimestamp
	@Column(nullable = false)	
	private LocalDateTime orderUpdatedTimeStamp;
	
	private LocalDateTime expectedDeliveryDate;

	private Integer orderQuantity;

	private Double orderTotalAmount;

	@OneToOne
	private Payment payment;

	@ManyToOne
	private Customer customer;

	@ElementCollection
	private List<OrderProductDetails> listOfProducts = new ArrayList<>();

	// Cancelled By Customer Product Should be added to Inventory
	private Boolean isOrderCancelled;

	// Returned By Customer Return Request
	// Should be Generated, After Confirming Pickup request should be generated,
	// after pickup, refund request or replace request should be generated Product
	// Should be added to Inventory,
	private Boolean isOrderReturned;

	// Replaced By Customer Product Should be Not be added to Inventory, Replacement
	// Request Should be Generated, After Confirming New Order Should be Placed
	private Boolean isOrderReplaced;

	// Refunded By BestBuy Product Should be added to Inventory, Refund Request
	// Should be Generated, And Payment Should be recieved by the Customer
	private Boolean isOrderRefunded;

	private Boolean isOrderDelievered;

	// Replacement Order
	private Boolean isReplacementOrder;

	// Replacement Order for Original Order
	private Integer orginialOrderId;


}
