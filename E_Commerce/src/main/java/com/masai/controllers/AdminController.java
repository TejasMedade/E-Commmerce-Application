/**
 * 
 */
package com.masai.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exceptions.ResourceNotFoundException;
import com.masai.modelRequestDto.AdminUpdateRequestDto;
import com.masai.modelResponseDto.AdminResponseDto;
import com.masai.payloads.ApiResponse;
import com.masai.services.AdminServices;

/**
 * @author tejas
 *
 */

@RestController
@RequestMapping("/bestbuy/admins")
public class AdminController {

	@Autowired
	private AdminServices adminServices;

	@PutMapping("/{adminId}")
	public ResponseEntity<AdminResponseDto> updateAdminDetailsHandler(@RequestBody AdminUpdateRequestDto userdto,
			@PathVariable("adminId") Integer adminId) throws ResourceNotFoundException {

		AdminResponseDto registerAdmin = this.adminServices.updateAdminDetails(userdto, adminId);

		return new ResponseEntity<AdminResponseDto>(registerAdmin, HttpStatus.OK);
	}

	@GetMapping("/{adminId}")
	public ResponseEntity<AdminResponseDto> getAdminDetailsByIdHandler(@PathVariable("adminId") Integer adminId)
			throws ResourceNotFoundException {

		AdminResponseDto adminResponseDto = this.adminServices.getAdminDetailsById(adminId);

		return new ResponseEntity<AdminResponseDto>(adminResponseDto, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<AdminResponseDto>> getAllAdminsHandler() {

		List<AdminResponseDto> allAdmins = this.adminServices.getAllAdmins();

		return new ResponseEntity<List<AdminResponseDto>>(allAdmins, HttpStatus.OK);
	}

	@DeleteMapping("/{adminId}")
	public ResponseEntity<ApiResponse> deleteAdminByIdHandler(@PathVariable("adminId") Integer adminId)
			throws ResourceNotFoundException {

		ApiResponse apiResponse = this.adminServices.deleteAdminById(adminId);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.GONE);
	}

}
