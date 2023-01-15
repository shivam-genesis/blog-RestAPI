package com.blog.services.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.entities.User;
import com.blog.entities.UserImage;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.UserImageDTO;
import com.blog.repositories.UserImageRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.UserImageService;

@Service
public class UserImageServiceImpl implements UserImageService {

	@Autowired
	private UserImageRepo userImageRepo;
	
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;
	
	private final String FOLDER_PATH = "/Users/shivamyadav/eclipse-workspace/blog-app/src/main/resources/userImages/";

	@Override
	public String addImage(Integer userId, String userImageId, MultipartFile file) throws IOException {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","Id: ",userId));
		String filePath = FOLDER_PATH + file.getOriginalFilename();
		UserImage userImage = this.userImageRepo
				.save(UserImage.builder().userImageId(userImageId).userImageName(file.getOriginalFilename())
						.userImageType(file.getContentType()).userImagePath(filePath).build());
		user.setUserImage(userImage);
		this.userRepo.save(user);
		file.transferTo(new File(filePath));

		if (userImage != null) {
			return "File Uploaded Successfully at Path: " + userImage.getUserImagePath();
		}

		return "File Not Uploaded!!";
	}
	
	@Override
	public byte[] getImageById(String userImageId) throws IOException {
		UserImage data = userImageRepo.findById(userImageId)
				.orElseThrow(() -> new ResourceNotFoundException("ImageData", "ImageId: ", Integer.parseInt(userImageId)));
		String filePath = data.getUserImagePath();
		byte[] image = Files.readAllBytes(new File(filePath).toPath());
		return image;
	}

	@Override
	public UserImageDTO getImageDetailsById(String userImageId) throws IOException {
		UserImage data = userImageRepo.findById(userImageId)
				.orElseThrow(() -> new ResourceNotFoundException("ImageData", "ImageId: ", Integer.parseInt(userImageId)));
		UserImageDTO dataDTO = this.modelMapper.map(data, UserImageDTO.class);
		return dataDTO;
	}

	@Override
	public String updateUserImage(String userImageId, UserImageDTO data) {

		return null;
	}

	@Override
	public void deleteUserImageById(String userImageId) {

	}
}