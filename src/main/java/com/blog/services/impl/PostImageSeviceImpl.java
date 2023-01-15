package com.blog.services.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.entities.Post;
import com.blog.entities.PostImage;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.PostImageDTO;
import com.blog.repositories.PostImageRepo;
import com.blog.repositories.PostRepo;
import com.blog.services.PostImageService;

@Service
public class PostImageSeviceImpl implements PostImageService {

	@Autowired
	private PostImageRepo postImageRepo;

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	private final String FOLDER_PATH = "/Users/shivamyadav/eclipse-workspace/blog-app/src/main/resources/postImages/";

	@Override
	public String addPostImage(Integer postId, Integer postImageId, MultipartFile file) throws IOException {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "ID: ", postId));
		String filePath = FOLDER_PATH + file.getOriginalFilename();
		PostImage postImage = this.postImageRepo
				.save(PostImage.builder().postImageId(postImageId).postImageName(file.getOriginalFilename())
						.postImageType(file.getContentType()).postImagePath(filePath).build());
		post.setPostImage(postImage);
		this.postRepo.save(post);
		file.transferTo(new File(filePath));

		if (postImage != null) {
			return "File Uploaded Successfully at Path: " + postImage.getPostImagePath();
		}
		return "File Not Uploaded!!";
	}

	@Override
	public byte[] getPostImageById(Integer postImageId) throws IOException {
		PostImage data = postImageRepo.findById(postImageId)
				.orElseThrow(() -> new ResourceNotFoundException("ImageData", "ID: ", postImageId));
		String filePath = data.getPostImagePath();
		byte[] image = Files.readAllBytes(new File(filePath).toPath());
		return image;
	}

	@Override
	public PostImageDTO getImageDetailsById(Integer postImageId) throws IOException {
		PostImage data = this.postImageRepo.findById(postImageId)
				.orElseThrow(() -> new ResourceNotFoundException("ImageData", "ID: ", postImageId));
		PostImageDTO dataDTO = this.modelMapper.map(data, PostImageDTO.class);
		return dataDTO;
	}

	@Override
	public String updatePostImage(Integer postImageId, PostImageDTO data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteImageById(Integer postImageId) {
		// TODO Auto-generated method stub

	}

}
