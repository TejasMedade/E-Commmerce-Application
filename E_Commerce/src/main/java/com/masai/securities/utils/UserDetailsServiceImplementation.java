/**
 * 
 */
package com.masai.securities.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.masai.repositories.CustomerRepo;

/**
 * @author tejas
 *
 */
@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

	@Autowired
	private CustomerRepo customerRepo;

	// Loading User From Database
	// Before this, Extend User by UserDetails
	@Override
	public UserDetails loadUserByUsername(String contact) throws UsernameNotFoundException {

		return customerRepo.findByContact(contact)
				.orElseThrow(() -> new UsernameNotFoundException("Customer Not Found with Contact : " + contact));

	}

}
