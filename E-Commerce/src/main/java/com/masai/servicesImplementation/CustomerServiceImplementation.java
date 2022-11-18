/**
 * 
 */
package com.masai.servicesImplementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exceptions.AdminException;
import com.masai.exceptions.CustomerException;
import com.masai.exceptions.LoginException;
import com.masai.model.Admin;
import com.masai.model.Cart;
import com.masai.model.Customer;
import com.masai.repository.CartRepo;
import com.masai.repository.CustomerRepo;
import com.masai.services.CustomerService;

/**
 * @author tejas
 *
 */
@Service
public class CustomerServiceImplementation implements CustomerService {

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private LoginLogoutCustomerServiceImplementation loginLogoutCustomerServiceimplementation;

	@Autowired
	private LoginLogoutAdminServiceImplementation loginLogoutAdminServiceimplementation;

	@Autowired
	private CartRepo cartRepo;

	@Override
	public Customer addCustomer(Customer customer) throws CustomerException {

		Cart cart = new Cart();

		cart.setCustomer(customer);

		Customer added_customer = customerRepo.save(customer);

		if (added_customer != null) {

			cartRepo.save(cart);

			return added_customer;

		} else {
			throw new CustomerException("OOps, Sign Up Unsuccessfull !");
		}
	}

	@Override
	public Customer updateCustomer(String key, Customer customer) throws CustomerException, LoginException {

		Customer validate_customer = loginLogoutCustomerServiceimplementation.validateCustomer(key);

		if (validate_customer != null) {

			return customerRepo.save(customer);

		} else {
			throw new CustomerException("Invalid Key, Please Login In !");
		}

	}

	@Override
	public String removeCustomer(String key, Integer customer_Id) throws CustomerException, LoginException {

		Customer validate_customer = loginLogoutCustomerServiceimplementation.validateCustomer(key);

		if (validate_customer != null) {

			customerRepo.deleteById(customer_Id);

			return "Customer Deleted Successfully !";

		} else {
			throw new CustomerException("Invalid Key, Please Login In !");
		}

	}

	@Override
	public Customer viewCustomer(String key, Integer customer_Id) throws CustomerException, LoginException {

		Customer validate_customer = loginLogoutCustomerServiceimplementation.validateCustomer(key);

		if (validate_customer != null) {

			Optional<Customer> optional_customer = customerRepo.findById(customer_Id);

			if (optional_customer.isPresent()) {

				return optional_customer.get();
			} else {
				throw new CustomerException("No Customer Found With The Customer Id : " + customer_Id);
			}

		} else {
			throw new CustomerException("Invalid Key, Please Login In !");
		}

	}

	@Override
	public List<Customer> viewAllCustomers(String key) throws AdminException, CustomerException, LoginException {

		Admin validate_admin = loginLogoutAdminServiceimplementation.validateAdmin(key);

		if (validate_admin != null) {

			List<Customer> listofcustomers = customerRepo.findAll();

			if (listofcustomers.isEmpty()) {
				throw new CustomerException("No Customers Available in the Database!");
			} else {
				return listofcustomers;
			}

		} else {
			throw new AdminException("Invalid Key, Please Login In as Admin!");
		}

	}

}
