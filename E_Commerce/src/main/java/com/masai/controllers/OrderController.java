/**
 * 
 */
package com.masai.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exceptions.DuplicateResourceException;
import com.masai.exceptions.ResourceNotAllowedException;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.modelResponseDto.OrderDetailsResponseDto;
import com.masai.modelResponseDto.RefundOrderDetailsResponseDto;
import com.masai.modelResponseDto.RefundOrderResponseDto;
import com.masai.modelResponseDto.ReplaceOrderDetailsResponseDto;
import com.masai.modelResponseDto.ReturnReplaceOrderResponseDto;
import com.masai.payloads.ApiResponse;
import com.masai.services.OrderServices;

/**
 * @author tejas
 *
 */

@RestController
@RequestMapping("/bestbuy/orders")
public class OrderController {

	@Autowired
	private OrderServices orderServices;

	@PostMapping("/customers/{contact}/payments/{paymentId}/products/{productId}/{quantity}")
	public ResponseEntity<ApiResponse> orderProduct(@PathVariable("contact") String contact,
			@PathVariable("paymentId") Integer paymentId, @PathVariable("productId") Integer productId,
			@PathVariable("quantity") Integer quantity) throws ResourceNotFoundException, ResourceNotAllowedException {

		ApiResponse apiResponse = this.orderServices.orderProduct(contact, paymentId, productId, quantity);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
	}

	@GetMapping("/customers/{contact}")
	public ResponseEntity<List<OrderDetailsResponseDto>> getAllOrdersByCustomer(@PathVariable("contact") String contact)
			throws ResourceNotFoundException {

		List<OrderDetailsResponseDto> allOrdersByCustomer = this.orderServices.getAllOrdersByCustomer(contact);

		return new ResponseEntity<List<OrderDetailsResponseDto>>(allOrdersByCustomer, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<OrderDetailsResponseDto>> getAllOrders() {

		List<OrderDetailsResponseDto> allOrders = this.orderServices.getAllOrders();

		return new ResponseEntity<List<OrderDetailsResponseDto>>(allOrders, HttpStatus.OK);
	}

	@DeleteMapping("admin/{orderId}/delete")
	public ResponseEntity<ApiResponse> deleteOrder(@PathVariable("orderId") Integer orderId)
			throws ResourceNotFoundException {

		ApiResponse apiResponse = this.orderServices.deleteOrder(orderId);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.GONE);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<OrderDetailsResponseDto> getOrderById(@PathVariable("orderId") Integer orderId)
			throws ResourceNotFoundException {

		OrderDetailsResponseDto orderByOrderId = this.orderServices.getOrderByOrderId(orderId);

		return new ResponseEntity<OrderDetailsResponseDto>(orderByOrderId, HttpStatus.OK);
	}

	@PutMapping("admin/{orderId}/delivered")
	public ResponseEntity<ApiResponse> markDelivered(@PathVariable("orderId") Integer orderId)
			throws ResourceNotFoundException {

		ApiResponse apiResponse = this.orderServices.markDelivered(orderId);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

	@PutMapping("admin/{orderId}/{status}")
	public ResponseEntity<OrderDetailsResponseDto> updateOrderStatus(@PathVariable("orderId") Integer orderId,
			@PathVariable("status") String status) throws ResourceNotFoundException {

		OrderDetailsResponseDto orderResponseDto = this.orderServices.updateOrderStatus(orderId, status);

		return new ResponseEntity<OrderDetailsResponseDto>(orderResponseDto, HttpStatus.OK);

	}

	@PostMapping("/{orderId}/cancel")
	public ResponseEntity<ApiResponse> cancelOrder(@PathVariable("orderId") Integer orderId)
			throws ResourceNotFoundException, DuplicateResourceException {

		ApiResponse apiResponse = this.orderServices.cancelOrder(orderId);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

	@GetMapping("/admin/refunds/requests/pending")
	public ResponseEntity<List<RefundOrderDetailsResponseDto>> getAllPendingRefundRequests() {

		List<RefundOrderDetailsResponseDto> pendingRefundRequests = this.orderServices.getAllPendingRefundRequests();

		return new ResponseEntity<List<RefundOrderDetailsResponseDto>>(pendingRefundRequests, HttpStatus.OK);
	}

	@GetMapping("/admin/replacements/requests/pending")
	public ResponseEntity<List<ReplaceOrderDetailsResponseDto>> getAllPendingReplacementRequests() {

		List<ReplaceOrderDetailsResponseDto> allPendingReplacementRequests = this.orderServices
				.getAllPendingReplacementRequests();

		return new ResponseEntity<List<ReplaceOrderDetailsResponseDto>>(allPendingReplacementRequests, HttpStatus.OK);
	}

	@GetMapping("/admin/replacements/requests/approved")
	public ResponseEntity<List<ReplaceOrderDetailsResponseDto>> getAllApprovedReplacementRequests() {

		List<ReplaceOrderDetailsResponseDto> allApprovedReplacementRequests = this.orderServices
				.getAllApprovedReplacementRequests();

		return new ResponseEntity<List<ReplaceOrderDetailsResponseDto>>(allApprovedReplacementRequests, HttpStatus.OK);

	}

	@GetMapping("/admin/refunds/requests/approved")
	public ResponseEntity<List<RefundOrderDetailsResponseDto>> getAllAprovedRefundRequests() {

		List<RefundOrderDetailsResponseDto> approvedRefundRequests = this.orderServices.getAllAprovedRefundRequests();

		return new ResponseEntity<List<RefundOrderDetailsResponseDto>>(approvedRefundRequests, HttpStatus.OK);
	}

	@PutMapping("/admin/refunds/{refundId}")
	public ResponseEntity<RefundOrderResponseDto> approveRefundsById(
			@PathVariable("refundId") Integer refundOrderRequestId) throws ResourceNotFoundException {

		RefundOrderResponseDto refundOrderResponseDto = this.orderServices.approveRefundById(refundOrderRequestId);

		return new ResponseEntity<RefundOrderResponseDto>(refundOrderResponseDto, HttpStatus.ACCEPTED);
	}

	@PutMapping("/admin/replacements/requests/{replaceId}/{approvedby}")
	public ResponseEntity<ReturnReplaceOrderResponseDto> approveReplacementRequest(
			@PathVariable("approvedby") String approvedBy, @PathVariable("replaceId") Integer replaceOrderRequestId)
			throws ResourceNotAllowedException, Exception {

		ReturnReplaceOrderResponseDto approveReplacementRequest = this.orderServices
				.approveReplacementRequest(approvedBy, replaceOrderRequestId);

		return new ResponseEntity<ReturnReplaceOrderResponseDto>(approveReplacementRequest, HttpStatus.OK);
	}

	@PostMapping("/{orderId}/return/replace")
	public ResponseEntity<ApiResponse> returnforReplacementAndPickupOrder(@PathVariable("orderId") Integer orderId)
			throws ResourceNotFoundException, DuplicateResourceException {

		ApiResponse apiResponse = this.orderServices.returnforReplacementAndPickupOrder(orderId);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

	@PostMapping("/{orderId}/return/refund")
	public ResponseEntity<ApiResponse> returnforRefundAndPickupOrder(@PathVariable("orderId") Integer orderId)
			throws ResourceNotFoundException, DuplicateResourceException {

		ApiResponse apiResponse = this.orderServices.returnforRefundAndPickupOrder(orderId);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

	}

	@PutMapping("/admin/pickups/requests/{orderId}/{pickedUpBy}")
	public ResponseEntity<ApiResponse> revokeOrderPickUpStatus(@PathVariable("pickedUpBy") String pickedUpBy,
			@PathVariable("orderId") Integer orderId) throws ResourceNotFoundException, DuplicateResourceException {

		ApiResponse apiResponse = this.orderServices.revokeOrderPickUpStatus(pickedUpBy, orderId);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

}
