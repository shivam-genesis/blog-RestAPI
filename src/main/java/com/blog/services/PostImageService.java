package com.blog.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.blog.payloads.PostImageDTO;

public interface PostImageService {
	
	String addPostImage(Integer postId, Integer postImageId, MultipartFile file) throws IOException;

	byte[] getPostImageById(Integer postImageId) throws IOException;
	
	PostImageDTO getImageDetailsById(Integer postImageId) throws IOException;
	
	String updatePostImage(Integer postImageId, PostImageDTO data);
	
	void deleteImageById(Integer postImageId);


}
