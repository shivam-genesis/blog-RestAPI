package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.configUser.UsersDownload;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.UserDTO;
import com.blog.repositories.UserRepo;
import com.blog.services.UserService;

@Service
//@Profile(value = { "local", "test", "prod" })
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		User user = this.userDTOToUser(userDTO);
		user.setEncodedPassword(this.passwordEncoder.encode(user.getPassword()));
		user.setRole("ROLE_" + user.getRole().toUpperCase());
		User newUser = this.userRepo.save(user);

		return this.userToUserDTO(newUser);
	}

	@Override
	public UserDTO getUserById(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User ", "Id: ", userId));
		return this.userToUserDTO(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users_list = this.userRepo.findAll();
		if (users_list.isEmpty()) {
			throw new ResourceNotFoundException("Users");
		}
		List<UserDTO> userDTOS = users_list.stream().map(user -> this.userToUserDTO(user)).collect(Collectors.toList());
		return userDTOS;
	}

	@Override
	public UserDTO updateEmail(int userId, String email) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User ", "Id: ", userId));
		user.setEmail(email);
		User updatedUser = this.userRepo.save(user);
		return this.userToUserDTO(updatedUser);
	}

	@Override
	public void updatePassword(int userId, String password) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User ", "Id: ", userId));
		user.setPassword(password);
		user.setEncodedPassword(this.passwordEncoder.encode(password));
		this.userRepo.save(user);
	}

	@Override
	public UserDTO updateAbout(int userId, String about) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User ", "Id: ", userId));
		user.setAbout(about);
		User updatedUser = this.userRepo.save(user);
		return this.userToUserDTO(updatedUser);
	}

	@Override
	public UserDTO updateRole(int userId, String role) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User ", "Id: ", userId));
		user.setRole("ROLE_" + role.toUpperCase());
		User updatedUser = this.userRepo.save(user);
		return this.userToUserDTO(updatedUser);
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User ", "Id: ", userId));
		this.userRepo.delete(user);
	}

	public void downloadToExcel(HttpServletResponse response) throws Exception {
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users.xlxs";
		response.setHeader(headerKey, headerValue);

		List<User> users = this.userRepo.findAll();
		if (users.isEmpty()) {
			throw new ResourceNotFoundException("Users");
		}
		UsersDownload usersDownload = new UsersDownload(users);
		usersDownload.export(response);
	}

	public User userDTOToUser(UserDTO userDTO) {
		User user = this.modelMapper.map(userDTO, User.class);
//		user.setId(userDTO.getId());
//		user.setName(userDTO.getName());
//		user.setEmail(userDTO.getEmail());
//		user.setPassword(userDTO.getPassword());
//		user.setAbout(userDTO.getAbout());
		return user;
	}

	public UserDTO userToUserDTO(User user) {
		UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);
//		userDTO.setId(user.getId());
//		userDTO.setName(user.getName());
//		userDTO.setEmail(user.getEmail());
//		userDTO.setPassword(user.getPassword());
//		userDTO.setAbout(user.getAbout());
		return userDTO;
	}
}