package com.masai;

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
import com.masai.model.Customer;
import com.masai.model.Role;
import com.masai.payloads.AppConstants;
import com.masai.repositories.CustomerRepo;
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

	}

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}
}
