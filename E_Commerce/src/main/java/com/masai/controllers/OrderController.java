/**
 * 
 */
package com.masai.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exceptions.DuplicateResourceException;
import com.masai.exceptions.ResourceNotAllowedException;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.model.Order;
import com.masai.model.PickUpOrderRequest;
import com.masai.model.RefundOrderRequest;
import com.masai.model.ReplaceOrderRequest;
import com.masai.modelResponseDto.OrderResponseDto;
import com.masai.modelResponseDto.PickUpOrderDetailsResponseDto;
import com.masai.modelResponseDto.RefundOrderDetailsResponseDto;
import com.masai.modelResponseDto.RefundOrderResponseDto;
import com.masai.modelResponseDto.ReplaceOrderDetailsResponseDto;
import com.masai.modelResponseDto.ReturnReplaceOrderResponseDto;
import com.masai.payloads.ApiResponse;
import com.masai.payloads.AppConstants;
import com.masai.payloads.OrderResponseModelAssembler;
import com.masai.payloads.PickUpOrderDetailsModelAssembler;
import com.masai.payloads.RefundOrderDetailsModelAssembler;
import com.masai.payloads.ReplaceOrderDetailsModelAssembler;
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

	@Autowired
	private OrderResponseModelAssembler orderResponseModelAssembler;

	@Autowired
	private PagedResourcesAssembler<Order> pagedResourcesAssembler;

	@Autowired
	private RefundOrderDetailsModelAssembler refundOrderDetailsModelAssembler;

	@Autowired
	private PagedResourcesAssembler<RefundOrderRequest> pagedRefundResourcesAssembler;

	@Autowired
	private ReplaceOrderDetailsModelAssembler replaceOrderDetailsModelAssembler;

	@Autowired
	private PagedResourcesAssembler<ReplaceOrderRequest> pagedReplaceResourcesAssembler;

	@Autowired
	private PickUpOrderDetailsModelAssembler pickUpOrderDetailsModelAssembler;

	@Autowired
	private PagedResourcesAssembler<PickUpOrderRequest> pagedPickupResourcesAssembler;

	@PostMapping("/customers/{contact}/payments/{paymentId}/products/{productId}/{quantity}")
	public ResponseEntity<ApiResponse> orderProduct(@PathVariable("contact") String contact,
			@PathVariable("paymentId") Integer paymentId, @PathVariable("productId") Integer productId,
			@PathVariable("quantity") Integer quantity) throws ResourceNotFoundException, ResourceNotAllowedException {

		ApiResponse apiResponse = this.orderServices.orderProduct(contact, paymentId, productId, quantity);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
	}

	@GetMapping("/customers/{contact}")
	public ResponseEntity<CollectionModel<OrderResponseDto>> getAllOrdersByCustomer(
			@PathVariable("contact") String contact,
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.ORDERSORTBY, required = false) String sortBy,
			@RequestParam(defaultValue = AppConstants.RATINGSORTDIRECTION, required = false) String sortDirection

	) throws ResourceNotFoundException {

		Page<Order> pageResponse = this.orderServices.getAllOrdersByCustomer(contact, pageNumber, pageSize, sortBy,
				sortDirection);

		PagedModel<OrderResponseDto> model = pagedResourcesAssembler.toModel(pageResponse, orderResponseModelAssembler);

		// Collection Link
		model.add(linkTo(methodOn(OrderController.class).getAllOrders(null, null, AppConstants.ORDERSORTBY,
				AppConstants.SORTDIRECTION)).withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CollectionModel<OrderResponseDto>>(model, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<CollectionModel<OrderResponseDto>> getAllOrders(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.ORDERSORTBY, required = false) String sortBy,
			@RequestParam(defaultValue = AppConstants.RATINGSORTDIRECTION, required = false) String sortDirection) {

		Page<Order> pageResponse = this.orderServices.getAllOrders(pageNumber, pageSize, sortBy, sortDirection);

		PagedModel<OrderResponseDto> model = pagedResourcesAssembler.toModel(pageResponse, orderResponseModelAssembler);

		// Collection Link
		model.add(linkTo(methodOn(OrderController.class).getAllOrders(null, null, AppConstants.ORDERSORTBY,
				AppConstants.SORTDIRECTION)).withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CollectionModel<OrderResponseDto>>(model, HttpStatus.OK);
	}

	@DeleteMapping("/admin/{orderId}/delete")
	public ResponseEntity<ApiResponse> deleteOrder(@PathVariable("orderId") Integer orderId)
			throws ResourceNotFoundException {

		ApiResponse apiResponse = this.orderServices.deleteOrder(orderId);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.GONE);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable("orderId") Integer orderId)
			throws ResourceNotFoundException {

		OrderResponseDto orderResponseDto = this.orderServices.getOrderByOrderId(orderId);

		// Self Link
		orderResponseDto
				.add(linkTo(methodOn(OrderController.class).getOrderById(orderResponseDto.getOrderId())).withSelfRel());

		// Collection Link
		orderResponseDto.add(linkTo(methodOn(OrderController.class).getAllOrders(null, null, AppConstants.ORDERSORTBY,
				AppConstants.SORTDIRECTION)).withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<OrderResponseDto>(orderResponseDto, HttpStatus.OK);
	}

	@PutMapping("/admin/{orderId}/delivered")
	public ResponseEntity<ApiResponse> markDelivered(@PathVariable("orderId") Integer orderId)
			throws ResourceNotFoundException {

		ApiResponse apiResponse = this.orderServices.markDelivered(orderId);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

	@PutMapping("/admin/{orderId}")
	public ResponseEntity<OrderResponseDto> updateOrderStatus(@PathVariable("orderId") Integer orderId,
			@RequestParam String status) throws ResourceNotFoundException {

		OrderResponseDto orderResponseDto = this.orderServices.updateOrderStatus(orderId, status);

		// Self Link
		orderResponseDto
				.add(linkTo(methodOn(OrderController.class).getOrderById(orderResponseDto.getOrderId())).withSelfRel());

		// Collection Link
		orderResponseDto.add(linkTo(methodOn(OrderController.class).getAllOrders(null, null, AppConstants.ORDERSORTBY,
				AppConstants.SORTDIRECTION)).withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<OrderResponseDto>(orderResponseDto, HttpStatus.OK);

	}

	@PostMapping("/{orderId}/cancel")
	public ResponseEntity<ApiResponse> cancelOrder(@PathVariable("orderId") Integer orderId)
			throws ResourceNotFoundException, DuplicateResourceException {

		ApiResponse apiResponse = this.orderServices.cancelOrder(orderId);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

	@GetMapping("/admin/refunds/requests/pending")
	public ResponseEntity<CollectionModel<RefundOrderDetailsResponseDto>> getAllPendingRefundRequests(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.REFUNDORDERSORTBY, required = false) String sortBy,
			@RequestParam(defaultValue = AppConstants.SORTDIRECTION, required = false) String sortDirection

	) {

		Page<RefundOrderRequest> pageResponse = this.orderServices.getAllPendingRefundRequests(pageNumber, pageSize,
				sortBy, sortDirection);

		PagedModel<RefundOrderDetailsResponseDto> model = pagedRefundResourcesAssembler.toModel(pageResponse,
				refundOrderDetailsModelAssembler);

		model.add(linkTo(methodOn(OrderController.class).getAllAprovedRefundRequests(pageNumber, pageSize, sortBy,
				sortDirection)).withRel("approved refunds"));

		return new ResponseEntity<CollectionModel<RefundOrderDetailsResponseDto>>(model, HttpStatus.OK);
	}

	@GetMapping("/admin/replacements/requests/pending")
	public ResponseEntity<CollectionModel<ReplaceOrderDetailsResponseDto>> getAllPendingReplacementRequests(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.REPLACEORDERSORTBY, required = false) String sortBy,
			@RequestParam(defaultValue = AppConstants.SORTDIRECTION, required = false) String sortDirection

	) {

		Page<ReplaceOrderRequest> pageResponse = this.orderServices.getAllPendingReplacementRequests(pageNumber,
				pageSize, sortBy, sortDirection);

		PagedModel<ReplaceOrderDetailsResponseDto> model = pagedReplaceResourcesAssembler.toModel(pageResponse,
				replaceOrderDetailsModelAssembler);

		model.add(linkTo(methodOn(OrderController.class).getAllApprovedReplacementRequests(pageNumber, pageSize, sortBy,
				sortDirection)).withRel("approved replacements"));

		return new ResponseEntity<CollectionModel<ReplaceOrderDetailsResponseDto>>(model, HttpStatus.OK);
	}

	@GetMapping("/admin/replacements/requests/approved")
	public ResponseEntity<CollectionModel<ReplaceOrderDetailsResponseDto>> getAllApprovedReplacementRequests(

			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.REPLACEORDERSORTBY, required = false) String sortBy,
			@RequestParam(defaultValue = AppConstants.SORTDIRECTION, required = false) String sortDirection

	) {

		Page<ReplaceOrderRequest> pageResponse = this.orderServices.getAllApprovedReplacementRequests(pageNumber,
				pageSize, sortBy, sortDirection);

		PagedModel<ReplaceOrderDetailsResponseDto> model = pagedReplaceResourcesAssembler.toModel(pageResponse,
				replaceOrderDetailsModelAssembler);

		model.add(linkTo(methodOn(OrderController.class).getAllPendingReplacementRequests(pageNumber, pageSize, sortBy,
				sortDirection)).withRel("replacements requests"));

		return new ResponseEntity<CollectionModel<ReplaceOrderDetailsResponseDto>>(model, HttpStatus.OK);

	}

	@GetMapping("/admin/refunds/requests/approved")
	public ResponseEntity<CollectionModel<RefundOrderDetailsResponseDto>> getAllAprovedRefundRequests(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.REFUNDORDERSORTBY, required = false) String sortBy,
			@RequestParam(defaultValue = AppConstants.SORTDIRECTION, required = false) String sortDirection) {

		Page<RefundOrderRequest> pageResponse = this.orderServices.getAllApprovedRefundRequests(pageNumber, pageSize,
				sortBy, sortDirection);

		PagedModel<RefundOrderDetailsResponseDto> model = pagedRefundResourcesAssembler.toModel(pageResponse,
				refundOrderDetailsModelAssembler);

		model.add(linkTo(methodOn(OrderController.class).getAllPendingRefundRequests(pageNumber, pageSize, sortBy,
				sortDirection)).withRel("refund requests"));

		return new ResponseEntity<CollectionModel<RefundOrderDetailsResponseDto>>(model, HttpStatus.OK);
	}

	@GetMapping("/admin/pickup/requests/pending")
	public ResponseEntity<CollectionModel<PickUpOrderDetailsResponseDto>> getAllPendingPickupRequests(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.PICKUPORDERSORTBY, required = false) String sortBy,
			@RequestParam(defaultValue = AppConstants.SORTDIRECTION, required = false) String sortDirection) {

		Page<PickUpOrderRequest> pageResponse = this.orderServices.getAllPendingPickupRequests(pageNumber, pageSize,
				sortBy, sortDirection);

		PagedModel<PickUpOrderDetailsResponseDto> model = pagedPickupResourcesAssembler.toModel(pageResponse,
				pickUpOrderDetailsModelAssembler);

		model.add(linkTo(methodOn(OrderController.class).getAllApprovedPickupRequests(pageNumber, pageSize, sortBy,
				sortDirection)).withRel("approved requests"));

		return new ResponseEntity<CollectionModel<PickUpOrderDetailsResponseDto>>(model, HttpStatus.OK);
	}

	@GetMapping("/admin/pickup/requests/approved")
	public ResponseEntity<CollectionModel<PickUpOrderDetailsResponseDto>> getAllApprovedPickupRequests(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.PICKUPORDERSORTBY, required = false) String sortBy,
			@RequestParam(defaultValue = AppConstants.SORTDIRECTION, required = false) String sortDirection) {

		Page<PickUpOrderRequest> pageResponse = this.orderServices.getAllApprovedPickupRequests(pageNumber, pageSize,
				sortBy, sortDirection);

		PagedModel<PickUpOrderDetailsResponseDto> model = pagedPickupResourcesAssembler.toModel(pageResponse,
				pickUpOrderDetailsModelAssembler);

		model.add(linkTo(methodOn(OrderController.class).getAllApprovedPickupRequests(pageNumber, pageSize, sortBy,
				sortDirection)).withRel("approved requests"));

		return new ResponseEntity<CollectionModel<PickUpOrderDetailsResponseDto>>(model, HttpStatus.OK);

	}
	@GetMapping("/customers/{contact}/replaced")
	public ResponseEntity<CollectionModel<ReplaceOrderDetailsResponseDto>> getAllReplacedOrdersByCustomer(@PathVariable("contact") String contact,
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.REPLACEORDERSORTBY, required = false) String sortBy,
			@RequestParam(defaultValue = AppConstants.SORTDIRECTION, required = false) String sortDirection)
			throws ResourceNotFoundException {

		Page<ReplaceOrderRequest> pageResponse = this.orderServices.getAllReplacedOrdersByCustomer(contact, pageNumber,
				pageSize, sortBy, sortDirection);

		PagedModel<ReplaceOrderDetailsResponseDto> model = pagedReplaceResourcesAssembler.toModel(pageResponse,
				replaceOrderDetailsModelAssembler);

		// Collection Link
		model.add(linkTo(methodOn(OrderController.class).getAllOrders(pageNumber, pageSize, AppConstants.ORDERSORTBY,
				AppConstants.SORTDIRECTION)).withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CollectionModel<ReplaceOrderDetailsResponseDto>>(model, HttpStatus.OK);
	}
	
	@GetMapping("/customers/{contact}/refunded")
	public ResponseEntity<CollectionModel<RefundOrderDetailsResponseDto>> getAllRefundOrdersByCustomer(
			@PathVariable("contact") String contact,
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.REFUNDORDERSORTBY, required = false) String sortBy,
			@RequestParam(defaultValue = AppConstants.SORTDIRECTION, required = false) String sortDirection)
			throws ResourceNotFoundException {

		Page<RefundOrderRequest> pageResponse = this.orderServices.getAllRefundOrdersByCustomer(contact, pageNumber,
				pageSize, sortBy, sortDirection);

		PagedModel<RefundOrderDetailsResponseDto> model = pagedRefundResourcesAssembler.toModel(pageResponse,
				refundOrderDetailsModelAssembler);

		// Collection Link
		model.add(linkTo(methodOn(OrderController.class).getAllOrders(pageNumber, pageSize, AppConstants.ORDERSORTBY,
				AppConstants.SORTDIRECTION)).withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CollectionModel<RefundOrderDetailsResponseDto>>(model, HttpStatus.OK);
	}

	@PutMapping("/admin/refunds/{refundId}")
	public ResponseEntity<RefundOrderResponseDto> approveRefundsById(@RequestParam String approvedBy,
			@PathVariable("refundId") Integer refundOrderRequestId) throws ResourceNotFoundException {

		RefundOrderResponseDto refundOrderResponseDto = this.orderServices.approveRefundById(approvedBy,
				refundOrderRequestId);

		return new ResponseEntity<RefundOrderResponseDto>(refundOrderResponseDto, HttpStatus.ACCEPTED);
	}

	@PutMapping("/admin/replacements/requests/{replaceId}")
	public ResponseEntity<ReturnReplaceOrderResponseDto> approveReplacementRequest(@RequestParam String approvedBy,
			@PathVariable("replaceId") Integer replaceOrderRequestId) throws Exception {

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

	@PutMapping("/admin/pickups/requests/{orderId}")
	public ResponseEntity<ApiResponse> revokeOrderPickUpStatus(@RequestParam("pickedUpBy") String pickedUpBy,
			@PathVariable("orderId") Integer orderId) throws ResourceNotFoundException, DuplicateResourceException {

		ApiResponse apiResponse = this.orderServices.revokeOrderPickUpStatus(pickedUpBy, orderId);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

}
