/**
 * 
 */
package com.masai.model;


import javax.persistence.Embeddable;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ASUS
 *
 */

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDetails extends RepresentationModel<OrderProductDetails> {

	private Integer productId;

	private String productName;

	private String productDescription;

	private Double salePrice;

	private Double marketPrice;

	private Boolean available;

	private Image images;

	private Integer productQuantity;

	private Double productTotalAmount;

	// Cancelled By Customer Product Should be added to Inventory
	private Boolean isProductCancelled;

	// Returned By Customer Product Should be added to Inventory, Return Request
	// Should be Generated, After Confirming Pickup request should be generated,
	// after pickup, refund request or replace request should be generated
	private Boolean isProductReturned;

	// Replaced By Customer Product Should be Not be added to Inventory, Replacement
	// Request Should be Generated, After Confirming New Order Should be Placed
	private Boolean isProductReplaced;

	// Refunded By BestBuy Product Should be added to Inventory, Refund Request
	// Should be Generated, And Payment Should be recieved by the Customer
	private Boolean isProductRefunded;

	private Boolean isProductDelievered;

	// Replacement Product
	private Boolean isReplacementProduct;

	// Replacement for Original Product
	private Integer orginialOrderId;


}
