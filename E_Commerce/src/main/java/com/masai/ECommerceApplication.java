package com.masai;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.masai.exceptions.ResourceNotFoundException;
import com.masai.model.Category;
import com.masai.model.Customer;
import com.masai.model.Payment;
import com.masai.model.Role;
import com.masai.modelResponseDto.CategoryResponseDto;
import com.masai.modelResponseDto.PaymentResponseDto;
import com.masai.payloads.AppConstants;
import com.masai.repositories.CategoryRepo;
import com.masai.repositories.CustomerRepo;
import com.masai.repositories.PaymentRepo;
import com.masai.repositories.RoleRepo;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ECommerceApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private PaymentRepo paymentRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	// Model Mapper Bean
	@Bean
	public ModelMapper modelMapper() {

		return new ModelMapper();

	}

	// Message Properties Bean
	@Bean
	public LocalValidatorFactoryBean validator(MessageSource ms) {

		LocalValidatorFactoryBean lvfb = new LocalValidatorFactoryBean();
		lvfb.setValidationMessageSource(ms);

		return lvfb;

	}

	@Override
	public void run(String... args) throws Exception {

		try {
			Role admin = new Role();
			admin.setRoleId(AppConstants.ROLE_ADMIN);
			admin.setRoleName("ROLE_ADMIN");

			Role user = new Role();
			user.setRoleId(AppConstants.ROLE_USER);
			user.setRoleName("ROLE_USER");

			List<Role> roles = List.of(admin, user);

			this.roleRepo.saveAll(roles);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Default Initial Admin Setup

		Role userRole = this.roleRepo.findById(AppConstants.ROLE_USER)
				.orElseThrow(() -> new ResourceNotFoundException("Role", "Role Id", AppConstants.ROLE_USER));

		Role adminRole = this.roleRepo.findById(AppConstants.ROLE_ADMIN)
				.orElseThrow(() -> new ResourceNotFoundException("Role", "Role Id", AppConstants.ROLE_ADMIN));

		boolean existsByEmail = customerRepo.existsByEmail("tejasmedade@gmail.com");

		if (existsByEmail) {

		} else {

			Customer admin = new Customer();

			admin.setContact("9307710594");
			admin.setEmail("tejasmedade@gmail.com");
			admin.setFirstName("Tejas");
			admin.setLastName("Medade");
			admin.setPassword(passwordEncoder.encode("Tejas@1998"));
			admin.getRoles().add(adminRole);
			admin.getRoles().add(userRole);

			this.customerRepo.save(admin);
		}

		// Payment Methods
		addPayments();
		// Category Methods
		addCategory();
		
		
	}

	public void addCategory() {

		List<Category> listofcategories = new ArrayList<>();

		Category beverages = new Category();

		beverages.setActive(true);
		beverages.setCategoryName("Beverages");
		beverages.setCategoryDescription(
				"The Beverages category offers a wide variety of drinks including soft drinks, juices, energy drinks, teas, coffees, and alcoholic beverages from different brands, it also includes options for dietary restrictions.");

		Category fashion = new Category();

		fashion.setActive(true);
		fashion.setCategoryName("Fashion");
		fashion.setCategoryDescription(
				"The Fashion category provides clothing, shoes and accessories for men, women and children, from different brands and styles, including casual and formal options. It caters to different sizes and fashion needs in one place.");

		Category electronics = new Category();

		electronics.setActive(true);
		electronics.setCategoryName("Electronics");
		electronics.setCategoryDescription(
				"The Electronics category includes consumer electronics like smartphones, laptops, tablets and more from various brands, including latest models and budget-friendly options. It offers a range of options for customers to choose.");

		listofcategories.add(beverages);
		listofcategories.add(fashion);
		listofcategories.add(electronics);

		for (Category category : listofcategories) {

			Category savedCategory = this.categoryRepo.save(category);

			CategoryResponseDto categoryResponseDto = this.modelMapper().map(savedCategory, CategoryResponseDto.class);
			System.out.println("New Caytegory Added : " + categoryResponseDto);

		}

	}

	public void addPayments() {

		List<Payment> listofpayments = new ArrayList<>();

		Payment credit = new Payment();

		credit.setPaymentId(101);
		credit.setPaymentType("Credit Card");
		credit.setAllowed(true);

		Payment debit = new Payment();

		debit.setPaymentId(102);
		debit.setPaymentType("Debit Card");
		debit.setAllowed(true);

		Payment cashOnDelivery = new Payment();

		cashOnDelivery.setPaymentId(103);
		cashOnDelivery.setPaymentType("Cash On Delivery");
		cashOnDelivery.setAllowed(true);

		Payment payPal = new Payment();

		payPal.setPaymentId(104);
		payPal.setPaymentType("Pay Pal");
		payPal.setAllowed(true);

		Payment netBanking = new Payment();

		credit.setPaymentId(105);
		credit.setPaymentType("Net Banking");
		credit.setAllowed(true);

		Payment wallet = new Payment();

		credit.setPaymentId(106);
		credit.setPaymentType("BestBuy Wallet");
		credit.setAllowed(true);

		listofpayments.add(credit);
		listofpayments.add(debit);
		listofpayments.add(cashOnDelivery);
		listofpayments.add(payPal);
		listofpayments.add(netBanking);
		listofpayments.add(wallet);

		for (Payment payment : listofpayments) {

			Payment savedPayment = this.paymentRepo.save(payment);
			System.out.println(
					"New Payment Method Added : " + this.modelMapper().map(savedPayment, PaymentResponseDto.class));
		}

	}

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}
}
