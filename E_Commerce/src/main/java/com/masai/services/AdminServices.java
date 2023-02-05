/**
 * 
 */
package com.masai.services;

import java.util.List;

import com.masai.exceptions.ResourceNotFoundException;
import com.masai.modelRequestDto.AdminRequestDto;
import com.masai.modelRequestDto.AdminUpdateRequestDto;
import com.masai.modelResponseDto.AdminResponseDto;
import com.masai.payloads.ApiResponse;

/**
 * @author tejas
 *
 */
public interface AdminServices {

	AdminResponseDto registerAdmin(AdminRequestDto adminRequestDto) throws ResourceNotFoundException;

	AdminResponseDto updateAdminDetails(AdminUpdateRequestDto userdto, Integer adminId)
			throws ResourceNotFoundException;

	AdminResponseDto getAdminDetailsById(Integer adminId) throws ResourceNotFoundException;

	List<AdminResponseDto> getAllAdmins();

	ApiResponse deleteAdminById(Integer adminId) throws ResourceNotFoundException;
	
	
}
