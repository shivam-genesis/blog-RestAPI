package com.blog.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.blog.payloads.UserImageDTO;

public interface UserImageService {
	String addImage(Integer userId, String userImageId, MultipartFile file) throws IOException;
	
	byte[] getImageById(String userImageId) throws IOException;

	UserImageDTO getImageDetailsById(String userImageId) throws IOException;

	String updateUserImage(String userImageId, UserImageDTO data);

	void deleteUserImageById(String userImageId);
}
