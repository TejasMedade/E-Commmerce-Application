/**
 * 
 */
package com.masai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exceptions.AdminException;
import com.masai.exceptions.LoginException;
import com.masai.exceptions.LogoutException;
import com.masai.exceptions.UserException;
import com.masai.model.Admin;
import com.masai.model.CurrentAdminSession;
import com.masai.model.User;
import com.masai.services.LoginLogoutAdminService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

/**
 * @author tejas
 *
 */

@RestController
@RequestMapping("/admin")
public class LoginLogoutAdminController {

	@Autowired
	private LoginLogoutAdminService loginLogoutAdminService;

	@GetMapping("/login")
	public ResponseEntity<CurrentAdminSession> loginAdminHandler(@RequestBody User user)
			throws LoginException, AdminException {

		CurrentAdminSession currentAdminSession = loginLogoutAdminService.loginAdmin(user);

		return new ResponseEntity<CurrentAdminSession>(currentAdminSession, HttpStatus.OK);
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logoutAdminHandler(@RequestParam String key) throws LogoutException {

		String result = loginLogoutAdminService.logoutAdmin(key);

		return new ResponseEntity<String>(result, HttpStatus.OK);

	}

	@GetMapping("/authenticate")
	public ResponseEntity<User> authenticateAdminHandler(@RequestBody User user, @RequestParam String key)
			throws UserException, AdminException, LoginException {

		User validated_admin = loginLogoutAdminService.authenticateAdmin(user, key);

		return new ResponseEntity<User>(validated_admin, HttpStatus.OK);
	}

	@GetMapping("/validate")
	public ResponseEntity<Admin> validateCustomerHandler(@RequestParam String key)
			throws AdminException, LoginException {

		Admin validated_admin = loginLogoutAdminService.validateAdmin(key);

		return new ResponseEntity<Admin>(validated_admin, HttpStatus.OK);
	}

}
