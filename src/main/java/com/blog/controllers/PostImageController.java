package com.blog.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.payloads.PostImageDTO;
import com.blog.services.PostImageService;

@RestController
public class PostImageController {

	@Autowired
	private PostImageService postImageService;

	@PostMapping("/upload/post/{postId}/image/{postImageId}")
	public ResponseEntity<String> addPostImage(@PathVariable("postId") Integer postId,
			@PathVariable("postImageId") Integer postImageId, @RequestParam("file") MultipartFile file)
			throws IOException {
		String s = this.postImageService.addPostImage(postId, postImageId, file);
		return new ResponseEntity<String>(s, HttpStatus.OK);
	}

	@GetMapping("/postImage/{postImageId}")
	public ResponseEntity<?> getPostImageById(@PathVariable("postImageId") Integer postImageId) throws IOException {
		byte[] image = this.postImageService.getPostImageById(postImageId);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
	}

	@GetMapping("/postImage-details/{postImageId}")
	public ResponseEntity<PostImageDTO> getImageDetailsById(@PathVariable("postImageId") Integer postImageId)
			throws IOException {
		PostImageDTO b = this.postImageService.getImageDetailsById(postImageId);
		return new ResponseEntity<PostImageDTO>(b, HttpStatus.OK);
	}
}