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

import com.blog.payloads.UserImageDTO;
import com.blog.services.UserImageService;

@RestController
public class UserImageController {

	@Autowired
	private UserImageService userImageService;

	@PostMapping("/upload/user/{userId}/image/{userImageId}")
	public ResponseEntity<String> addImage(@PathVariable("userId") Integer userId,
			@PathVariable("userImageId") String userImageId, @RequestParam("file") MultipartFile file)
			throws IOException {
		String s = this.userImageService.addImage(userId, userImageId, file);
		return new ResponseEntity<String>(s, HttpStatus.OK);
	}

	@GetMapping("/userImage/{userImageId}")
	public ResponseEntity<?> getImageById(@PathVariable("userImageId") String userImageId) throws IOException {
		byte[] image = this.userImageService.getImageById(userImageId);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
	}

	@GetMapping("/userImage-details/{userImageId}")
	public ResponseEntity<UserImageDTO> getImageDetailsById(@PathVariable("userImageId") String userImageId)
			throws IOException {
		UserImageDTO b = this.userImageService.getImageDetailsById(userImageId);
		return new ResponseEntity<UserImageDTO>(b, HttpStatus.OK);
	}
}