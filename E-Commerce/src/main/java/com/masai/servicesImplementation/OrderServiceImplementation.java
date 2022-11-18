/**
 * 
 */
package com.masai.servicesImplementation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.dto.ProductDTO;
import com.masai.exceptions.AdminException;
import com.masai.exceptions.CartException;
import com.masai.exceptions.CustomerException;
import com.masai.exceptions.LoginException;
import com.masai.exceptions.OrderException;
import com.masai.exceptions.ProductException;
import com.masai.exceptions.UserException;
import com.masai.model.Admin;
import com.masai.model.Cart;
import com.masai.model.Customer;
import com.masai.model.Order;
import com.masai.model.Product;
import com.masai.model.User;
import com.masai.repository.CartRepo;
import com.masai.repository.CustomerRepo;
import com.masai.repository.OrderRepo;
import com.masai.repository.ProductRepo;
import com.masai.services.LoginLogoutAdminService;
import com.masai.services.LoginLogoutCustomerService;
import com.masai.services.OrderService;

/**
 * @author tejas
 *
 */

@Service
public class OrderServiceImplementation implements OrderService {

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private LoginLogoutCustomerService loginLogoutCustomerServiceImplementation;

	@Autowired
	private LoginLogoutAdminService loginLogoutAdminServiceImplementation;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private ProductRepo productRepo;

	@Override
	public List<Order> viewAllOrdersbyUserId(User user, String key)
			throws OrderException, UserException, LoginException, CustomerException {

		User validate_user = loginLogoutCustomerServiceImplementation.authenticateCustomer(user, key);

		if (validate_user != null) {

			Optional<Customer> optional_customer = customerRepo.findByMobileNumber(user.getId());

			if (optional_customer.isPresent()) {

				Customer customer = optional_customer.get();

				List<Order> listoforders = customer.getListOfOrders();

				if (!listoforders.isEmpty()) {

					return listoforders;

				} else {
					throw new OrderException("No Orders Are Been Placed With The Customer_Id : " + user.getId());
				}
			} else {
				throw new CustomerException("No Customer Registered With The User Id : " + user.getId());
			}

		} else {
			throw new UserException("User Authentication Failed, Please Login In !");
		}

	}

	@Override
	public Order removeOrder(Integer orderId, String key) throws OrderException, LoginException, CustomerException {

		Customer customer = loginLogoutCustomerServiceImplementation.validateCustomer(key);

		if (customer != null) {

			Optional<Order> optional_order = orderRepo.findById(orderId);

			if (optional_order.isPresent()) {

				Order order = optional_order.get();

				order.setOrderStatus("Cancelled");

				List<ProductDTO> listofproducts = optional_order.get().getProductDtoList();

				for (int i = 0; i < listofproducts.size(); i++) {

					ProductDTO productDTO = listofproducts.get(i);

					Integer deleted_quantity = productDTO.getQuantity();

					Optional<Product> optional_product = productRepo.findById(productDTO.getProductId());

					Product product = optional_product.get();

					Integer database_quantity = product.getQuantity();

					product.setQuantity(database_quantity + deleted_quantity);

					productRepo.save(product);
				}

				return orderRepo.save(order);

			} else {
				throw new OrderException("No Orders Are Found With This Order_Id : " + orderId);
			}

		} else {
			throw new CustomerException("No Customer Found, Please Login In !");
		}
	}

	@Override
	public List<Order> viewallOrdersByDate(String key, String stringdate)
			throws OrderException, CustomerException, LoginException {

		LocalDate date = LocalDate.parse(stringdate);

		Customer customer = loginLogoutCustomerServiceImplementation.validateCustomer(key);

		if (customer != null) {

			List<Order> listOfOrdersByLocalDate = orderRepo.findByorderDate(date);

			if (!listOfOrdersByLocalDate.isEmpty()) {

				return listOfOrdersByLocalDate;
			} else {
				throw new OrderException("No Orders Found For This Date : " + date);
			}

		} else {
			throw new CustomerException("No Customer Found, Please Login In !");
		}

	}

	@Override
	public List<Order> viewAllOrdersByLocation(String key, String location)
			throws OrderException, LoginException, AdminException {

		Admin admin = loginLogoutAdminServiceImplementation.validateAdmin(key);

		if (admin != null) {

			List<Order> listOfOrdersByLocation = orderRepo.findBylocation(location);

			if (!listOfOrdersByLocation.isEmpty()) {

				return listOfOrdersByLocation;

			} else {
				throw new OrderException("No Orders Found For This Location : " + location);
			}

		} else {
			throw new AdminException("No Customer Found, Please Login In !");
		}
	}

	@Override
	public Order addOrder(String key)
			throws LoginException, CustomerException, OrderException, CartException, ProductException {

		Customer customer = loginLogoutCustomerServiceImplementation.validateCustomer(key);

		if (customer != null) {

			Optional<Cart> optional_cart = cartRepo.findByCustomer(customer);

			List<Order> listoforders = customer.getListOfOrders();

			if (!optional_cart.isEmpty()) {

				Cart cart = optional_cart.get();

				Order order = new Order();

				order.setCustomer(customer);
				order.setAddress(customer.getAddress());
				order.setLocation(customer.getAddress().getCity());
				order.setOrderDate(LocalDate.now());
				order.setOrderStatus("Order Confirmed");

				List<ProductDTO> listofcartproducts = cart.getProducts();

				if (!listofcartproducts.isEmpty()) {

					Double totalprice = 0.0;

					List<ProductDTO> listoforderedproducts = new ArrayList<>();

					for (int i = 0; i < listofcartproducts.size(); i++) {

						ProductDTO cart_product = listofcartproducts.get(i);

						Optional<Product> optional_product = productRepo.findById(cart_product.getProductId());

						if (optional_product.isPresent()) {

							Product product = optional_product.get();

							Integer available_quantity = product.getQuantity();

							if (available_quantity >= cart_product.getQuantity()) {

								Double price = cart_product.getPrice() * cart_product.getQuantity();

								totalprice = totalprice + price;

								product.setQuantity(available_quantity - cart_product.getQuantity());

								productRepo.save(product);

								listoforderedproducts.add(cart_product);
							} else {
								throw new ProductException(
										"Oops ! Available Quantity of Products : " + available_quantity);
							}

						} else {

							throw new ProductException(
									"No Product Found With This Product Id : " + cart_product.getProductId());
						}

					}

					order.setTotal(totalprice);

					order.setProductDtoList(listoforderedproducts);

					listoforders.add(order);

					customer.setListOfOrders(listoforders);

					cart.setProducts(new ArrayList<>());

					cartRepo.save(cart);

					return orderRepo.save(order);

				} else {

					throw new OrderException("Cart is Empty, Please Add Products To Place an Order !");
				}

			} else {
				throw new CartException("No Cart Found With This Customer Id : " + customer.getCustomerId());
			}

		} else {
			throw new CustomerException("No Customer Found, Please Login In !");
		}

	}

	@Override
	public List<Order> viewOrder(String key) throws LoginException, CustomerException, OrderException {

		Customer customer = loginLogoutCustomerServiceImplementation.validateCustomer(key);

		if (customer != null) {

			List<Order> listOfOrders = customer.getListOfOrders();

			if (!listOfOrders.isEmpty()) {

				return listOfOrders;

			} else {
				throw new OrderException("No Orders Found For You !");
			}

		} else {
			throw new CustomerException("No Customer Found, Please Login In !");
		}

	}

}
