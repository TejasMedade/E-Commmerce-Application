/**
 * 
 */
package com.masai.servicesImplementation;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.masai.controllers.CustomerController;
import com.masai.controllers.OrderController;
import com.masai.controllers.PaymentController;
import com.masai.controllers.ProductController;
import com.masai.exceptions.DuplicateResourceException;
import com.masai.exceptions.ResourceNotAllowedException;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.model.CancelOrderRequest;
import com.masai.model.Customer;
import com.masai.model.Order;
import com.masai.model.OrderProductDetails;
import com.masai.model.Payment;
import com.masai.model.PickUpOrderRequest;
import com.masai.model.Product;
import com.masai.model.RefundOrderRequest;
import com.masai.model.ReplaceOrderRequest;
import com.masai.model.ReturnOrderRequest;
import com.masai.modelResponseDto.CancelOrderResponseDto;
import com.masai.modelResponseDto.OrderDetailsResponseDto;
import com.masai.modelResponseDto.OrderResponseDto;
import com.masai.modelResponseDto.RefundOrderResponseDto;
import com.masai.modelResponseDto.ReturnRefundOrderResponseDto;
import com.masai.modelResponseDto.ReturnReplaceOrderResponseDto;
import com.masai.payloads.ApiResponse;
import com.masai.repositories.CancelOrderRequestRepo;
import com.masai.repositories.CustomerRepo;
import com.masai.repositories.OrderRepo;
import com.masai.repositories.PaymentRepo;
import com.masai.repositories.PickUpOrderRequestRepo;
import com.masai.repositories.ProductRepo;
import com.masai.repositories.RefundOrderRequestRepo;
import com.masai.repositories.ReplaceOrderRequestRepo;
import com.masai.repositories.ReturnOrderRequestRepo;
import com.masai.services.OrderServices;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author tejas
 *
 */
@Service
public class OrderServicesImplementation implements OrderServices {

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private PaymentRepo paymentRepo;

	@Autowired
	private PickUpOrderRequestRepo pickUpOrderRequestRepo;

	@Autowired
	private RefundOrderRequestRepo refundOrderRequestRepo;

	@Autowired
	private ReplaceOrderRequestRepo replaceOrderRequestRepo;

	@Autowired
	private ReturnOrderRequestRepo returnOrderRequestRepo;

	@Autowired
	private CancelOrderRequestRepo cancelOrderRequestRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ApiResponse orderProduct(String contact, Integer paymentId, Integer productId, Integer quantity)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		Payment payment = this.paymentRepo.findById(paymentId)
				.orElseThrow(() -> new ResourceNotFoundException("Payment", "Payment Id", paymentId));

		if (Boolean.TRUE.equals(payment.getAllowed())) {

			Product product = this.productRepo.findById(productId)
					.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", productId));

			if (product.getAvailable()) {

				Integer stockQuantity = product.getStockQuantity();

				if (stockQuantity < quantity) {

					return new ApiResponse(LocalDateTime.now(),
							"Unfortunately, Limited Stock Available ! Requested Quantity Is Greater Than Existing Stock !",
							false);

				} else {

					Customer customer = this.customerRepo.findByContact(contact)
							.orElseThrow(() -> new ResourceNotFoundException("Customer", "Contact Number", contact));

					Double orderTotalAmount = quantity * product.getSalePrice();

					List<OrderProductDetails> listOfProductDetails = new ArrayList<OrderProductDetails>();

					OrderProductDetails orderProductDetails = this.toOrderProductDetails(product);

					// Self Product Link
					orderProductDetails.add(
							linkTo(methodOn(ProductController.class).getProductByIdHandler(productId)).withSelfRel());

					orderProductDetails.setProductQuantity(quantity);
					orderProductDetails.setProductTotalAmount(orderTotalAmount);

					listOfProductDetails.add(orderProductDetails);

					Order order = new Order();

					order.setOrderStatus("Order Placed");
					order.setExpectedDeliveryDate(LocalDateTime.now().plusDays(3));
					order.setOrderQuantity(quantity);
					order.setOrderTotalAmount(orderTotalAmount);
					order.setPayment(payment);
					order.setCustomer(customer);
					order.setListOfProducts(listOfProductDetails);

					order.setIsOrderCancelled(false);
					order.setIsOrderReturned(false);
					order.setIsOrderReplaced(false);
					order.setIsOrderRefunded(false);
					order.setIsOrderDelievered(false);
					order.setIsReplacementOrder(false);

					Order savedOrder = this.orderRepo.save(order);

					product.setStockQuantity(stockQuantity - quantity);

					this.productRepo.save(product);

					OrderDetailsResponseDto orderResponseDto = this.toOrderDetailsResponseDto(savedOrder);

					// Self Link
					orderResponseDto
							.add(linkTo(methodOn(OrderController.class).getOrderById(orderResponseDto.getOrderId()))
									.withSelfRel());

					// Customer Link
					orderResponseDto.add(
							linkTo(methodOn(CustomerController.class).getCustomerHandler(contact)).withRel("customer"));

					// Payment Link
					orderResponseDto.add(linkTo(methodOn(PaymentController.class).getPaymentMethodHandler(paymentId))
							.withRel("payment"));

					return new ApiResponse(LocalDateTime.now(), "Order Placed Successfully !", true, orderResponseDto);
				}

			} else {

				throw new ResourceNotAllowedException("Product", "Product Id", productId);

			}

		} else {

			throw new ResourceNotAllowedException("Payment", "Payment Id", paymentId);

		}

	}

	@Override
	public Page<Order> getAllOrdersByCustomer(String contact, Integer pageNumber, Integer pageSize, String sortBy,
			String sortDirection) throws ResourceNotFoundException {

		Customer customer = this.customerRepo.findByContact(contact)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "Contact Number", contact));

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		return this.orderRepo.findByCustomer(customer, pageable);

	}

	@Override
	public Page<Order> getAllOrders(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		return this.orderRepo.findAll(pageable);

	}

	@Override
	public ApiResponse deleteOrder(Integer orderId) throws ResourceNotFoundException {

		Order order = this.orderRepo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order", "Order Id", orderId));

		this.orderRepo.delete(order);

		return new ApiResponse(LocalDateTime.now(), "Order Deleted Successfully !", true, order);
	}

	@Override
	public ApiResponse markDelivered(Integer orderId) throws ResourceNotFoundException {

		Order order = this.orderRepo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order", "Order Id", orderId));

		List<OrderProductDetails> listOfProducts = order.getListOfProducts();

		listOfProducts.forEach(p -> p.setIsProductDelievered(true));

		order.setIsOrderDelievered(true);
		order.setOrderStatus("Delievered");
		order.setExpectedDeliveryDate(LocalDateTime.now());

		System.out.println(order.getCustomer());

		Order savedOrder = this.orderRepo.save(order);

		OrderResponseDto orderResponseDto = this.toOrderResponseDto(savedOrder);

		return new ApiResponse(LocalDateTime.now(), "Order Delieverd Successfully !", true, orderResponseDto);
	}

	@Override
	public OrderResponseDto updateOrderStatus(Integer orderId, String status) throws ResourceNotFoundException {

		Order order = this.orderRepo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order", "Order Id", orderId));

		order.setOrderStatus(status);

		Order savedOrder = this.orderRepo.save(order);

		return this.toOrderResponseDto(savedOrder);

	}

	@Override // Perfect Method
	public ApiResponse cancelOrder(Integer orderId) throws ResourceNotFoundException, DuplicateResourceException {

		Order order = this.orderRepo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order", "Order Id", orderId));

		if (Boolean.TRUE.equals(order.getIsOrderDelievered())) {

			return new ApiResponse(LocalDateTime.now(),
					"Order is Already Delivered, Please Request for Returning Order !", false, order);

		} else {

			Optional<CancelOrderRequest> findByOrder = this.cancelOrderRequestRepo.findByOrder(order);

			if (findByOrder.isEmpty()) {

				order.setIsOrderCancelled(true);
				order.setIsOrderRefunded(true);
				order.setOrderStatus("Cancelled & Refunded");

				CancelOrderRequest cancelOrderRequest = new CancelOrderRequest();

				cancelOrderRequest.setCustomer(order.getCustomer());
				cancelOrderRequest.setStatus("Order Cancelled !");
				cancelOrderRequest.setOrder(order);
				cancelOrderRequest.setIsOrderCancelled(true);
				cancelOrderRequest.setIsOrderRefunded(true);

				if (order.getPayment().getPaymentId() == 103) {

					CancelOrderRequest savedCancelOrderRequest = this.cancelOrderRequestRepo.save(cancelOrderRequest);

					CancelOrderResponseDto cancelOrderResponseDto = this
							.toCancelOrderResponseDto(savedCancelOrderRequest);

					return new ApiResponse(LocalDateTime.now(), "Order Cancelled Succesfully !", true,
							cancelOrderResponseDto);

				} else {

					cancelOrderRequest.setStatus("Order Cancelled, Refund Requested !");

					RefundOrderRequest refundOrderRequest = new RefundOrderRequest();

					refundOrderRequest.setCustomer(order.getCustomer());
					refundOrderRequest.setApproved(true);
					refundOrderRequest.setApprovedBy("System Approved Refund");
					refundOrderRequest.setOrder(order);
					refundOrderRequest.setPayment(order.getPayment());
					refundOrderRequest.setOrderTotalAmount(order.getOrderTotalAmount());
					refundOrderRequest.setStatus("Refund Successfull !");

					cancelOrderRequest.setRefundOrderRequest(refundOrderRequest);

					CancelOrderRequest savedCancelOrderRequest = this.cancelOrderRequestRepo.save(cancelOrderRequest);

					CancelOrderResponseDto cancelOrderResponseDto = this
							.toCancelOrderResponseDto(savedCancelOrderRequest);

					return new ApiResponse(LocalDateTime.now(),
							"Order Cancelled Succesfully, Refund Request In Process !", true, cancelOrderResponseDto);

				}

			} else {

				throw new DuplicateResourceException("Cancel Order Request", "Order Id", orderId);

			}

		}

	}

	@Override // Perfect Method
	public ApiResponse returnforRefundAndPickupOrder(Integer orderId)
			throws ResourceNotFoundException, DuplicateResourceException {

		Order order = this.orderRepo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order", "Order Id", orderId));

		if (Boolean.TRUE.equals(order.getIsOrderDelievered())) {

			order.setOrderStatus("Returned");

			Optional<ReturnOrderRequest> optional = this.returnOrderRequestRepo.findByOrder(order);

			if (optional.isEmpty()) {

				if (Boolean.TRUE.equals(order.getIsOrderDelievered())) {

					ReturnOrderRequest returnOrderRequest = new ReturnOrderRequest();

					returnOrderRequest.setOrder(order);
					returnOrderRequest.setIsReturnOrderPickedup(false);
					returnOrderRequest.setStatus("Return Requested, Order Pickup In Process!");
					returnOrderRequest.setReturnForReplacement(false);
					returnOrderRequest.setReturnForRefund(true);
					returnOrderRequest.setCustomer(order.getCustomer());

					PickUpOrderRequest pickUpOrderRequest = new PickUpOrderRequest();

					pickUpOrderRequest.setCustomer(order.getCustomer());
					pickUpOrderRequest.setOrder(order);
					pickUpOrderRequest.setExpectedPickUpDate(LocalDateTime.now().plusDays(1));
					pickUpOrderRequest.setIsReturnOrderPickedUp(false);

					PickUpOrderRequest savedPickUpOrderRequest = this.pickUpOrderRequestRepo.save(pickUpOrderRequest);

					returnOrderRequest.setPickUpOrderRequest(savedPickUpOrderRequest);

					ReturnOrderRequest savedReturnOrderRequest = this.returnOrderRequestRepo.save(returnOrderRequest);

					ReturnRefundOrderResponseDto returnRefundOrderResponseDto = this
							.toReturnRefundOrderResponseDto(savedReturnOrderRequest);

					return new ApiResponse(LocalDateTime.now(), "Return Requested, Order Pickup In Process!", true,
							returnRefundOrderResponseDto);

				}

				else {

					return new ApiResponse(LocalDateTime.now(), "Order is Not Yet Delivered , Please Cancel The Order",
							false, order);

				}

			} else {

				throw new DuplicateResourceException("Return Order Request", "Order Id", orderId);
			}

		} else {

			return new ApiResponse(LocalDateTime.now(), "Order is Not Delivered, Please Cancel The Order !", false,
					order);

		}

	}

	@Override // Perfect Method
	public ApiResponse returnforReplacementAndPickupOrder(Integer orderId)
			throws ResourceNotFoundException, DuplicateResourceException {

		Order order = this.orderRepo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order", "Order Id", orderId));

		if (Boolean.TRUE.equals(order.getIsOrderDelievered())) {

			order.setOrderStatus("Returned");

			Optional<ReturnOrderRequest> optional = this.returnOrderRequestRepo.findByOrder(order);

			if (optional.isEmpty()) {

				if (Boolean.TRUE.equals(order.getIsOrderDelievered())) {

					ReturnOrderRequest returnOrderRequest = new ReturnOrderRequest();

					returnOrderRequest.setCustomer(order.getCustomer());
					returnOrderRequest.setOrder(order);
					returnOrderRequest.setIsReturnOrderPickedup(false);
					returnOrderRequest.setStatus("Return Requested, Order Pickup In Process!");
					returnOrderRequest.setReturnForReplacement(true);
					returnOrderRequest.setReturnForRefund(false);

					PickUpOrderRequest pickUpOrderRequest = new PickUpOrderRequest();

					pickUpOrderRequest.setCustomer(order.getCustomer());
					pickUpOrderRequest.setOrder(order);
					pickUpOrderRequest.setIsReturnOrderPickedUp(false);
					pickUpOrderRequest.setExpectedPickUpDate(LocalDateTime.now().plusDays(1));

					PickUpOrderRequest savedPickUpOrderRequest = this.pickUpOrderRequestRepo.save(pickUpOrderRequest);

					returnOrderRequest.setPickUpOrderRequest(savedPickUpOrderRequest);

					ReturnOrderRequest savedReturnOrderRequest = this.returnOrderRequestRepo.save(returnOrderRequest);

					ReturnReplaceOrderResponseDto returnReplaceOrderResponseDto = this
							.toReturnReplaceOrderResponseDto(savedReturnOrderRequest);

					return new ApiResponse(LocalDateTime.now(), "Return Requested, Order Pickup In Process!", true,
							returnReplaceOrderResponseDto);

				}

				else {

					return new ApiResponse(LocalDateTime.now(), "Order is Not Yet Delivered , Please Cancel The Order",
							false, order);

				}

			}

			else {

				throw new DuplicateResourceException("Return Order Request", "Order Id", orderId);

			}

		} else {

			return new ApiResponse(LocalDateTime.now(), "Order is Not Delivered, Please Cancel The Order !", false,
					order);
		}

	}

	@Override
	public ApiResponse revokeOrderPickUpStatus(String pickedUpBy, Integer orderId)
			throws ResourceNotFoundException, DuplicateResourceException {

		Order order = this.orderRepo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order", "Order Id", orderId));

		Optional<RefundOrderRequest> refundOptional = this.refundOrderRequestRepo.findByOrder(order);

		Optional<ReplaceOrderRequest> replaceOptional = replaceOrderRequestRepo.findByOrder(order);

		if (refundOptional.isPresent()) {

			throw new DuplicateResourceException("Refund Order Request", "Order Id", orderId);

		} else if (replaceOptional.isPresent()) {

			throw new DuplicateResourceException("Replace Order Request", "Order Id", orderId);

		} else {

			order.setIsOrderReturned(true);
			order.setOrderStatus("Return Picked Up");

			ReturnOrderRequest returnOrderRequest = this.returnOrderRequestRepo.findByOrder(order)
					.orElseThrow(() -> new ResourceNotFoundException("Order", "Order Id", orderId));

			returnOrderRequest.setIsReturnOrderPickedup(true);
			returnOrderRequest.setStatus("Order PickUp Successfull !");

			PickUpOrderRequest pickUpOrderRequest = returnOrderRequest.getPickUpOrderRequest();

			pickUpOrderRequest.setIsReturnOrderPickedUp(true);
			pickUpOrderRequest.setPickedUpBy(pickedUpBy);

			if (Boolean.TRUE.equals(returnOrderRequest.getReturnForRefund())) {

				RefundOrderRequest refundOrderRequest = new RefundOrderRequest();

				refundOrderRequest.setCustomer(order.getCustomer());
				refundOrderRequest.setApproved(false);
				refundOrderRequest.setOrder(order);
				refundOrderRequest.setPayment(order.getPayment());
				refundOrderRequest.setOrderTotalAmount(order.getOrderTotalAmount());
				refundOrderRequest.setStatus("Refund Requested, Waiting for Approval !");

				returnOrderRequest.setRefundOrderRequest(refundOrderRequest);

				ReturnOrderRequest savedReturnOrderRequest = this.returnOrderRequestRepo.save(returnOrderRequest);

				ReturnRefundOrderResponseDto returnRefundOrderResponseDto = this
						.toReturnRefundOrderResponseDto(savedReturnOrderRequest);

				return new ApiResponse(LocalDateTime.now(), "Order Returned Successfully ! Refund Requested !", true,
						returnRefundOrderResponseDto);

			} else {

				ReplaceOrderRequest replaceOrderRequest = new ReplaceOrderRequest();

				replaceOrderRequest.setCustomer(order.getCustomer());
				replaceOrderRequest.setApproved(false);
				replaceOrderRequest.setOrder(order);
				replaceOrderRequest.setIsReplacementOrderGenerated(false);
				replaceOrderRequest.setStatus("Replacement Requested, Waiting for Approval !");

				returnOrderRequest.setReplaceOrderRequest(replaceOrderRequest);

				ReturnOrderRequest savedReturnOrderRequest = this.returnOrderRequestRepo.save(returnOrderRequest);

				ReturnReplaceOrderResponseDto returnReplaceOrderResponseDto = this
						.toReturnReplaceOrderResponseDto(savedReturnOrderRequest);

				return new ApiResponse(LocalDateTime.now(), "Order Returned Successfully ! Replacement Requested !",
						true, returnReplaceOrderResponseDto);
			}

		}

	}

	@Override
	public Page<RefundOrderRequest> getAllApprovedRefundRequests(Integer pageNumber, Integer pageSize, String sortBy,
			String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		return this.refundOrderRequestRepo.findByApproved(true, pageable);

	}

	@Override
	public Page<RefundOrderRequest> getAllPendingRefundRequests(Integer pageNumber, Integer pageSize, String sortBy,
			String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		return this.refundOrderRequestRepo.findByApproved(false, pageable);

	}

	@Override
	public Page<ReplaceOrderRequest> getAllPendingReplacementRequests(Integer pageNumber, Integer pageSize,
			String sortBy, String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		return this.replaceOrderRequestRepo.findByApproved(false, pageable);

	}

	@Override
	public Page<ReplaceOrderRequest> getAllApprovedReplacementRequests(Integer pageNumber, Integer pageSize,
			String sortBy, String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		return this.replaceOrderRequestRepo.findByApproved(true, pageable);

	}

	@Override
	public Page<PickUpOrderRequest> getAllPendingPickupRequests(Integer pageNumber, Integer pageSize, String sortBy,
			String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		return this.pickUpOrderRequestRepo.findByIsReturnOrderPickedUp(false, pageable);

	}

	@Override
	public Page<PickUpOrderRequest> getAllApprovedPickupRequests(Integer pageNumber, Integer pageSize, String sortBy,
			String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		return this.pickUpOrderRequestRepo.findByIsReturnOrderPickedUp(true, pageable);

	}

	@Override
	public Page<ReplaceOrderRequest> getAllReplacedOrdersByCustomer(String contact, Integer pageNumber,
			Integer pageSize, String sortBy, String sortDirection) throws ResourceNotFoundException {

		Customer customer = this.customerRepo.findByContact(contact)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "Contact Number", contact));

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		return this.replaceOrderRequestRepo.findByCustomer(customer, pageable);

	}

	@Override
	public Page<RefundOrderRequest> getAllRefundOrdersByCustomer(String contact, Integer pageNumber, Integer pageSize,
			String sortBy, String sortDirection) throws ResourceNotFoundException {

		Customer customer = this.customerRepo.findByContact(contact)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "Contact Number", contact));

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		return this.refundOrderRequestRepo.findByCustomer(customer, pageable);

	}

	@Override
	public OrderResponseDto getOrderByOrderId(Integer orderId) throws ResourceNotFoundException {

		Order order = this.orderRepo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order", "Order Id", orderId));

		return this.toOrderResponseDto(order);
	}

	@Override
	public ReturnReplaceOrderResponseDto approveReplacementRequest(String approvedBy, Integer replaceOrderRequestId)
			throws Exception {

		ReplaceOrderRequest replaceOrderRequest = this.replaceOrderRequestRepo.findById(replaceOrderRequestId)
				.orElseThrow(() -> new ResourceNotFoundException("Replacement Request", "Replacement Request Id",
						replaceOrderRequestId));

		ReturnOrderRequest returnOrderRequest = this.returnOrderRequestRepo
				.findByReplaceOrderRequest(replaceOrderRequest)
				.orElseThrow(() -> new ResourceNotFoundException("Return Order Request", "Replace Order Request Id",
						replaceOrderRequestId));

		returnOrderRequest.setStatus("Order Replaced Successfully !");

		Order originalOrder = replaceOrderRequest.getOrder();

		originalOrder.setIsOrderReplaced(true);
		originalOrder.setOrderStatus("Replaced");
		Payment payment = originalOrder.getPayment();
		Customer customer = originalOrder.getCustomer();
		List<OrderProductDetails> listOfOriginalProducts = originalOrder.getListOfProducts();

		List<OrderProductDetails> listOfProducts = new ArrayList<>();

		for (OrderProductDetails o : listOfOriginalProducts) {

			Integer productId = o.getProductId();
			Integer productQuantity = o.getProductQuantity();

			Product product = this.productRepo.findById(productId)
					.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", productId));

			if (product.getAvailable()) {

				if (product.getStockQuantity() < productQuantity) {

					throw new Exception(
							"Unfortunately, Limited Stock Available ! Requested Quantity Is Greater Than Existing Stock !");

				} else {

					Integer stockQuantity = product.getStockQuantity();

					product.setStockQuantity(stockQuantity - productQuantity);

					this.productRepo.save(product);

					listOfProducts.add(o);

				}

			} else {

				throw new ResourceNotAllowedException("Product", "Product Id", productId);
			}

		}

		Order order = new Order();

		order.setOrderStatus("Order Placed");
		order.setExpectedDeliveryDate(LocalDateTime.now().plusDays(3));
		order.setOrderQuantity(originalOrder.getOrderQuantity());
		order.setOrderTotalAmount(originalOrder.getOrderTotalAmount());
		order.setPayment(payment);
		order.setCustomer(customer);
		order.setListOfProducts(listOfProducts);

		order.setIsOrderCancelled(false);
		order.setIsOrderReturned(false);
		order.setIsOrderReplaced(false);
		order.setIsOrderRefunded(false);
		order.setIsOrderDelievered(false);
		order.setIsReplacementOrder(true);

		order.setOrginialOrderId(originalOrder.getOrderId());

		Order savedOrder = this.orderRepo.save(order);

		this.orderRepo.save(originalOrder);

		replaceOrderRequest.setApproved(true);
		replaceOrderRequest.setApprovedBy(approvedBy);
		replaceOrderRequest.setIsReplacementOrderGenerated(true);
		replaceOrderRequest.setStatus("Replacement Order is Placed Successfully !");
		replaceOrderRequest.setReplacemenetOrderId(savedOrder.getOrderId());

		ReturnOrderRequest savedReturnOrderRequest = this.returnOrderRequestRepo.save(returnOrderRequest);

		return this.toReturnReplaceOrderResponseDto(savedReturnOrderRequest);

	}

	@Override
	public RefundOrderResponseDto approveRefundById(String approvedBy, Integer refundOrderRequestId)
			throws ResourceNotFoundException {

		RefundOrderRequest refundOrderRequest = this.refundOrderRequestRepo.findById(refundOrderRequestId).orElseThrow(
				() -> new ResourceNotFoundException("Refund Request", "Refund Request Id", refundOrderRequestId));

		ReturnOrderRequest returnOrderRequest = this.returnOrderRequestRepo.findByRefundOrderRequest(refundOrderRequest)
				.orElseThrow(() -> new ResourceNotFoundException("Return Order Request", "Refund Order Request Id",
						refundOrderRequestId));

		Order order = refundOrderRequest.getOrder();

		order.setIsOrderRefunded(true);
		order.setOrderStatus("Refunded");

		Order savedOrder = this.orderRepo.save(order);

		refundOrderRequest.setApproved(true);
		refundOrderRequest.setStatus("Refund Successfull !");
		refundOrderRequest.setOrder(savedOrder);
		refundOrderRequest.setApprovedBy(approvedBy);

		ReturnOrderRequest savedReturnOrderRequest = this.returnOrderRequestRepo.save(returnOrderRequest);

		return this.toRefundOrderResponseDto(savedReturnOrderRequest.getRefundOrderRequest());

	}

	// ModelMapper Methods

	private OrderProductDetails toOrderProductDetails(Product product) {

		return this.modelMapper.map(product, OrderProductDetails.class);
	}

	private CancelOrderResponseDto toCancelOrderResponseDto(CancelOrderRequest cancelOrderRequest) {

		return this.modelMapper.map(cancelOrderRequest, CancelOrderResponseDto.class);

	}

	private ReturnRefundOrderResponseDto toReturnRefundOrderResponseDto(ReturnOrderRequest returnOrderRequest) {

		return this.modelMapper.map(returnOrderRequest, ReturnRefundOrderResponseDto.class);
	}

	private ReturnReplaceOrderResponseDto toReturnReplaceOrderResponseDto(ReturnOrderRequest returnOrderRequest) {

		return this.modelMapper.map(returnOrderRequest, ReturnReplaceOrderResponseDto.class);
	}

	private RefundOrderResponseDto toRefundOrderResponseDto(RefundOrderRequest refundOrderRequest) {

		return this.modelMapper.map(refundOrderRequest, RefundOrderResponseDto.class);
	}

	private OrderDetailsResponseDto toOrderDetailsResponseDto(Order order) {

		return this.modelMapper.map(order, OrderDetailsResponseDto.class);

	}

	private OrderResponseDto toOrderResponseDto(Order order) {

		return this.modelMapper.map(order, OrderResponseDto.class);

	}

	@Override
	public Page<Order> getSalesMadeToday(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
		LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));

		return this.orderRepo.findAllByOrderTimeStampBetween(today, end, pageable);

	}

	@Override
	public Page<Order> getSalesMadeThisWeek(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		LocalDateTime start = LocalDateTime.of(LocalDate.now().with(DayOfWeek.MONDAY), LocalTime.of(0, 0, 0));
		LocalDateTime end = LocalDateTime.of(LocalDate.now().with(DayOfWeek.SUNDAY), LocalTime.of(0, 0, 0));

		return this.orderRepo.findAllByOrderTimeStampBetween(start, end, pageable);
	}

	@Override
	public Page<Order> getSalesMadeLastWeek(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		LocalDateTime start = LocalDateTime.of(LocalDate.now().minusWeeks(1).with(DayOfWeek.MONDAY),
				LocalTime.of(0, 0, 0));
		LocalDateTime end = LocalDateTime.of(LocalDate.now().minusWeeks(1).with(DayOfWeek.SUNDAY),
				LocalTime.of(0, 0, 0));

		return this.orderRepo.findAllByOrderTimeStampBetween(start, end, pageable);
	}

	@Override
	public Page<Order> getSalesMadeMonth(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		LocalDateTime start = LocalDateTime.of(LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()),
				LocalTime.of(0, 0, 0));

		LocalDateTime end = LocalDateTime.of(LocalDate.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()),
				LocalTime.of(0, 0, 0));

		return this.orderRepo.findAllByOrderTimeStampBetween(start, end, pageable);
	}

	@Override
	public Page<Order> getSalesMadeJanDec(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		LocalDateTime start = LocalDateTime.of(LocalDate.now().minusYears(1).with(TemporalAdjusters.firstDayOfYear()),
				LocalTime.of(0, 0, 0));

		LocalDateTime end = LocalDateTime.of(LocalDate.now().minusYears(1).with(TemporalAdjusters.lastDayOfYear()),
				LocalTime.of(0, 0, 0));

		return this.orderRepo.findAllByOrderTimeStampBetween(start, end, pageable);
	}

}
