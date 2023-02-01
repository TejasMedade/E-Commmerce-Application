/**
 * 
 */
package com.masai.servicesImplementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exceptions.ResourceNotAllowedException;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.model.Cart;
import com.masai.model.CartProductDetails;
import com.masai.model.Customer;
import com.masai.model.Order;
import com.masai.model.OrderProductDetails;
import com.masai.model.Payment;
import com.masai.model.Product;
import com.masai.modelResponseDto.CartResponseDto;
import com.masai.modelResponseDto.OrderResponseDto;
import com.masai.payloads.ApiResponse;
import com.masai.repositories.CustomerRepo;
import com.masai.repositories.OrderRepo;
import com.masai.repositories.PaymentRepo;
import com.masai.repositories.ProductRepo;
import com.masai.services.CartServices;

/**
 * @author tejas
 *
 */
@Service
public class CartServiceImplementation implements CartServices {

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private PaymentRepo paymentRepo;

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private ModelMapper modelMapper;

//	private Double orderTotalAmount;
//
//	private Integer orderQuantity;

	@Override
	public ApiResponse addProducttoCart(String contact, Integer productId, Integer productQuantity)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", productId));

		if (product.getAvailable()) {

			if (product.getStockQuantity() < productQuantity) {

				return new ApiResponse(LocalDateTime.now(),
						"Unfortunately, Limited Stock Available ! Requested Quantity Is Greater Than Existing Stock !",
						false);

			} else {

				Customer customer = this.customerRepo.findByContact(contact)
						.orElseThrow(() -> new ResourceNotFoundException("Customer", "Contact Number", contact));

				Cart cart = customer.getCart();

				List<CartProductDetails> listOfProducts = cart.getListOfProducts();

				boolean flag = false;

				CartProductDetails cartProductDetails = null;

				int index = -1;

				for (int i = 0; i < listOfProducts.size(); i++) {

					if (Objects.equals(listOfProducts.get(i).getProductId(), productId)) {

						flag = true;

						cartProductDetails = listOfProducts.get(i);

						index = i;

						break;
					}

				}

				if (flag) {

					// Updating Product Quantity

					Integer originalProductQuantity = cartProductDetails.getProductQuantity();

					cartProductDetails.setProductQuantity(originalProductQuantity + productQuantity);

					cartProductDetails.setProductTotalAmount(
							(originalProductQuantity + productQuantity) * cartProductDetails.getSalePrice());

					// Updating Cart Quantity

					Integer cartQuantity = cart.getCartQuantity();

					Double cartTotalAmount = cart.getCartTotalAmount();

					cart.setCartQuantity(cartQuantity + productQuantity);

					cart.setCartTotalAmount(cartTotalAmount + (productQuantity * product.getSalePrice()));

					// Updating List of Cart Products

					listOfProducts.remove(index);

					listOfProducts.add(index, cartProductDetails);

					cart.setListOfProducts(listOfProducts);

					customer.setCart(cart);

					Customer savedCustomer = this.customerRepo.save(customer);

					return new ApiResponse(LocalDateTime.now(),
							"Product Already Added to Cart, Quantity Updated Successfully !", true,
							this.toCartResponseDto(savedCustomer.getCart()));

				} else {

					// Updating Cart Quantity

					Integer cartQuantity = cart.getCartQuantity();

					Double cartTotalAmount = cart.getCartTotalAmount();

					cart.setCartQuantity(cartQuantity + productQuantity);

					cart.setCartTotalAmount(cartTotalAmount + (productQuantity * product.getSalePrice()));

					// Setting New Cart Product Details

					CartProductDetails cartProductDetails2 = this.toCartProductDetails(product);

					cartProductDetails2.setProductQuantity(productQuantity);

					cartProductDetails2.setProductTotalAmount(productQuantity * product.getSalePrice());

					// Updating List of Cart Products

					listOfProducts.add(cartProductDetails2);

					cart.setListOfProducts(listOfProducts);

					customer.setCart(cart);

					Customer savedCustomer = this.customerRepo.save(customer);

					return new ApiResponse(LocalDateTime.now(), "Product Added to Cart Successfully !", true,
							this.toCartResponseDto(savedCustomer.getCart()));

				}

			}

		} else {
			throw new ResourceNotAllowedException("Product", "Product Id", productId);
		}

	}

	@Override
	public ApiResponse updateCartProductQuantity(String contact, Integer productId, Integer quantity)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", productId));

		if (product.getAvailable()) {

			if (product.getStockQuantity() < quantity) {

				return new ApiResponse(LocalDateTime.now(),
						"Unfortunately, Limited Stock Available ! Requested Quantity Is Greater Than Existing Stock !",
						false);

			} else {

				Double salePrice = product.getSalePrice();

				Double price = salePrice * quantity;

				Customer customer = this.customerRepo.findByContact(contact)
						.orElseThrow(() -> new ResourceNotFoundException("Customer", "Contact Number", contact));

				Cart cart = customer.getCart();

				cart.getListOfProducts().stream().forEach(p -> {

					if (Objects.equals(p.getProductId(), productId)) {

						p.setProductQuantity(p.getProductQuantity() + quantity);

						p.setProductTotalAmount(p.getProductTotalAmount() + price);

						cart.setCartQuantity(cart.getCartQuantity() + quantity);

						cart.setCartTotalAmount(cart.getCartTotalAmount() + price);

					}

				});

				customer.setCart(cart);

				Customer savedCustomer = this.customerRepo.save(customer);

				CartResponseDto cartResponseDto = this.toCartResponseDto(savedCustomer.getCart());

				return new ApiResponse(LocalDateTime.now(), "Quantity Updated Successfully !", true, cartResponseDto);
			}
		} else {
			throw new ResourceNotAllowedException("Product", "Product Id", productId);
		}

	}

	@Override
	public CartResponseDto deleteProductfromCart(String contact, Integer productId) throws ResourceNotFoundException {

		Customer customer = this.customerRepo.findByContact(contact)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "Contact Number", contact));

		Cart cart = customer.getCart();

		List<CartProductDetails> remove = new ArrayList<>();

		cart.getListOfProducts().stream().forEach(p -> {

			if (Objects.equals(p.getProductId(), productId)) {

				remove.add(p);

			}

		});

		CartProductDetails cartProductDetails = remove.get(0);

		cart.setCartQuantity(cart.getCartQuantity() - cartProductDetails.getProductQuantity());
		cart.setCartTotalAmount(cart.getCartTotalAmount() - cartProductDetails.getProductTotalAmount());

		boolean status = cart.getListOfProducts().removeAll(remove);

		if (status) {

			Customer savedCustomer = this.customerRepo.save(customer);

			return this.toCartResponseDto(savedCustomer.getCart());

		} else {
			throw new ResourceNotFoundException("Product", "Product Id", productId);
		}
	}

	@Override
	public CartResponseDto emptyCart(String contact) throws ResourceNotFoundException {

		Customer customer = this.customerRepo.findByContact(contact)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "Contact Number", contact));

		Cart cart = customer.getCart();

		cart.setCartTotalAmount(0.0);
		cart.setCartQuantity(0);

		cart.getListOfProducts().clear();

		Customer savedCustomer = this.customerRepo.save(customer);

		return this.toCartResponseDto(savedCustomer.getCart());

	}

	@Override
	public CartResponseDto getCart(String contact) throws ResourceNotFoundException {

		Customer customer = this.customerRepo.findByContact(contact)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "Contact Number", contact));

		Cart cart = customer.getCart();

		return this.toCartResponseDto(cart);

	}

	@Override
	public OrderResponseDto buyCart(String contact, Integer paymentId)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		Payment payment = this.paymentRepo.findById(paymentId)
				.orElseThrow(() -> new ResourceNotFoundException("Payment", "Payment Id", paymentId));

		if (Boolean.TRUE.equals(payment.getAllowed())) {

			Customer customer = this.customerRepo.findByContact(contact)
					.orElseThrow(() -> new ResourceNotFoundException("Customer", "Contact Number", contact));

			if (customer.getAddress() == null) {

				throw new ResourceNotFoundException("Address", "Registered Customer Id", customer.getUserId());

			} else {

				Cart cart = customer.getCart();

				List<CartProductDetails> listOfProducts = cart.getListOfProducts();

				if (listOfProducts.isEmpty()) {

					throw new ResourceNotFoundException("Cart");

				} else {

					List<OrderProductDetails> listOfProductDetails = new ArrayList<>();

					for (CartProductDetails p : listOfProducts) {

						Product product = this.productRepo.findById(p.getProductId()).get();

						if (product.getAvailable()) {

							Integer stockQuantity = product.getStockQuantity();

							Long totalSales = product.getTotalSales();

							totalSales = totalSales + p.getProductQuantity();

							product.setStockQuantity(stockQuantity - p.getProductQuantity());

							product.setTotalSales(totalSales);

							this.productRepo.save(product);

							listOfProductDetails.add(this.toOrderProductDetails(p));

						} else {

							throw new ResourceNotAllowedException("Product", "Product Id", product.getProductId());

						}

					}

					Order order = new Order();

					order.setOrderStatus("Order Placed");
					order.setExpectedDeliveryDate(LocalDateTime.now().plusDays(3));
					order.setOrderQuantity(cart.getCartQuantity());
					order.setOrderTotalAmount(cart.getCartTotalAmount());
					order.setPayment(payment);
					order.setCustomer(customer);
					order.setListOfProducts(listOfProductDetails);

					order.setIsOrderCancelled(false);
					order.setIsOrderReturned(false);
					order.setIsOrderReplaced(false);
					order.setIsOrderRefunded(false);
					order.setIsOrderDelievered(false);
					order.setIsReplacementOrder(false);

					listOfProducts.clear();

					cart.setListOfProducts(listOfProducts);
					cart.setCartQuantity(0);
					cart.setCartTotalAmount(0.0);

					customer.setCart(cart);

					this.customerRepo.save(customer);

					Order savedOrder = this.orderRepo.save(order);

					return this.toOrderResponseDto(savedOrder);

				}

			}

		} else {
			throw new ResourceNotAllowedException("Payment", "Payment Id", paymentId);
		}

	}

	// Model Mappper Methods

	private CartResponseDto toCartResponseDto(Cart cart) {

		return this.modelMapper.map(cart, CartResponseDto.class);

	}

	private CartProductDetails toCartProductDetails(Product product) {

		return this.modelMapper.map(product, CartProductDetails.class);
	}

	private OrderProductDetails toOrderProductDetails(CartProductDetails cartProductDetails) {

		return this.modelMapper.map(cartProductDetails, OrderProductDetails.class);
	}

	private OrderResponseDto toOrderResponseDto(Order order) {

		return this.modelMapper.map(order, OrderResponseDto.class);
	}
}
