/**
 * 
 */
package com.masai.spring.securities.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.masai.JWT.security.configurations.JWTAuthenticationEntryPoint;
import com.masai.JWT.security.configurations.JWTAuthenticationFilter;

/**
 * @author tejas
 *
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration {

	public static final String[] PUBLIC_URLS = {

			"/bestbuy/auth/customers/signup", "/bestbuy/auth/customers/signin", "/bestbuy/auth/admins/signin",
			"/bestbuy/categories/all", "/bestbuy/categories/all/**", "/bestbuy/categories/{categoryId}",
			"/bestbuy/products/all", "/bestbuy/products/all/**", "/bestbuy/products/{productId}",
			"/bestbuy/products/image/{imageName}", "/bestbuy/products/{productId}/images", "/bestbuy/reviews/**",
			"/v3/api-docs", "/v2/api-docs", "/swagger-resources/**", "/swagger-ui/**", "/webjars/**" };

	public static final String[] USER_ADMIN_URLS = { "/bestbuy/auth/signout", "/bestbuy/address/**",
			"/bestbuy/carts/**", "/bestbuy/customers/{contact}", "/bestbuy/customers/{contact}/image",
			"/bestbuy/customers/{contact}/update", "/bestbuy/customers/{contact}/delete",
			"/bestbuy/customers/images/{imageName}", "/bestbuy/customers/images/{imageName}/delete",
			"/bestbuy/feedbacks/**",
			"/bestbuy/orders/customers/{contact}/payments/{paymentId}/products/{productId}/{quantity}",
			"/bestbuy/orders/customers/{contact}", "/bestbuy/orders/customers/{contact}/replaced",
			"/bestbuy/orders/customers/{contact}/refunded", "/bestbuy/orders/{orderId}",
			"/bestbuy/orders/{orderId}/cancel", "/bestbuy/orders/{orderId}/return/replace",
			"/bestbuy/orders/{orderId}/return/refund", "/bestbuy/orders/{orderId}/return/replace",
			"/bestbuy/payments/{paymentId}", "/bestbuy/payments/all", "/bestbuy/reviews/{reviewId}/delete" };

	public static final String[] ADMIN_URLS = { "/bestbuy/admins/**", "/bestbuy/customers/",
			"/bestbuy/customers/search/**",
			"/bestbuy/categories/", "/bestbuy/categories/{categoryId}/update",
			"/bestbuy/categories/{categoryId}/delete", "/bestbuy/orders/", "/bestbuy/orders/admin/**",
			"/bestbuy/payments/", "/bestbuy/payments/{paymentId}/delete", "/bestbuy/payments/",
			"/bestbuy/payments/{paymentId}/revoke", "/bestbuy/products/admin/**"

	};

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JWTAuthenticationEntryPoint authenticationEntryPoint;

	// Basic Authentication for API
	// Needs a Better Way, as we need to enter UserName & Password from
	// application.properties
	// every time we fire a request.
	// So JWT Token came in to picture.

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers(PUBLIC_URLS).permitAll().antMatchers(ADMIN_URLS).hasRole("ADMIN")
				.antMatchers(USER_ADMIN_URLS).hasAnyRole("USER", "ADMIN").anyRequest().authenticated();

		http.authenticationProvider(authenticationProvider());

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	// Basic Authentication for API
	// Entering Username & Password from Database
	// Build UserDetails By Extending User
	// Build UserServiceDetails Implementation

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService);

		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	// Password Endcoder Required

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public JWTAuthenticationFilter authenticationJwtTokenFilter() {
		return new JWTAuthenticationFilter();
	}

}
