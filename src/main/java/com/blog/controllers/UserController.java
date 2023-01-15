package com.blog.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.exceptions.ApiResponse;
import com.blog.payloads.UserDTO;
import com.blog.services.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	// POST -> Create User
	@PostMapping("/user")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
		UserDTO createUserDTO = this.userService.createUser(userDTO);
		return new ResponseEntity<UserDTO>(createUserDTO, HttpStatus.CREATED);
	}

	// GET -> Get Specific User
	@GetMapping("/user/{userId}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Integer userId) {
		UserDTO userDTO = this.userService.getUserById(userId);
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	}

	// GET -> Get Users
	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		List<UserDTO> users = this.userService.getAllUsers();
		return new ResponseEntity<List<UserDTO>>(users, HttpStatus.OK);
	}

	// PUT -> Update User
	@PutMapping("/user/email/{userId}")
	public ResponseEntity<UserDTO> updateEmail(@PathVariable("userId") int userId, @RequestParam String email) {
		UserDTO updateUserDTO = this.userService.updateEmail(userId, email);
		return ResponseEntity.ok(updateUserDTO);
	}

	// Put -> Update User
	@PutMapping("/user/password/{userId}")
	public ResponseEntity<ApiResponse> updatePassword(@PathVariable("userId") int userId,
			@RequestParam String password) {
		this.userService.updatePassword(userId, password);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Password Updated!!", true), HttpStatus.OK);
	}

	@PutMapping("/user/about/{userId}")
	public ResponseEntity<UserDTO> updateAbout(@PathVariable("userId") int userId, @RequestParam String about) {
		UserDTO u = this.userService.updateAbout(userId, about);
		return new ResponseEntity<UserDTO>(u, HttpStatus.OK);
	}

	@PutMapping("/user/role/{userId}")
	public ResponseEntity<UserDTO> updateRole(@PathVariable("userId") int userId, @RequestParam String role) {
		UserDTO u = this.userService.updateRole(userId, role);
		return new ResponseEntity<UserDTO>(u, HttpStatus.OK);
	}

	// ADMIN
	// DELETE -> Delete User
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/user/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
		this.userService.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted!!", true), HttpStatus.OK);
	}

	// Get Users Data into Excel file
	@GetMapping("/users/download")
	public ResponseEntity<ApiResponse> downloadToExcel(HttpServletResponse response) throws Exception {
		this.userService.downloadToExcel(response);
		return new ResponseEntity<ApiResponse>(new ApiResponse("File Downloaded!!", true), HttpStatus.OK);
	}

}