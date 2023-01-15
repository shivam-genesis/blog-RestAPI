package com.blog.services;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.blog.payloads.UserDTO;

@Service
public interface UserService {
	UserDTO createUser(UserDTO userDTO);

	UserDTO getUserById(Integer userId);

	List<UserDTO> getAllUsers();
	
	UserDTO updateEmail(int userId, String email);
	
	void updatePassword(int userId, String password);
	
	UserDTO updateAbout(int userId, String about);
	
	UserDTO updateRole(int userId, String role);
	
	void deleteUser(Integer userId);
	
	void downloadToExcel(HttpServletResponse response) throws Exception;
	
//	User userDTOToUser(UserDTO userDTO);
//	
//	UserDTO userToUserDTO(User user);
}
