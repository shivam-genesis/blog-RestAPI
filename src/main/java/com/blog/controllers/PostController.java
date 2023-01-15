package com.blog.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.config.AppConstants;
import com.blog.configPost.PostResponse;
import com.blog.exceptions.ApiResponse;
import com.blog.payloads.PostDTO;
import com.blog.services.PostService;

@RestController
public class PostController {

	@Autowired
	private PostService postService;

	// POST -> Create Post
	@PostMapping("/post/user/{userId}/category/{categoryId}")
	public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		PostDTO createPostDTO = this.postService.createPost(postDTO, userId, categoryId);
		return new ResponseEntity<>(createPostDTO, HttpStatus.CREATED);
	}

	// Get Post By Id
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable Integer postId) {
		PostDTO post = this.postService.getPostById(postId);
		return new ResponseEntity<PostDTO>(post, HttpStatus.OK);
	}

	// Get By User
	@GetMapping("/posts/user/{userId}")
	public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable Integer userId) {
		List<PostDTO> posts = this.postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
	}

	// Get By Category
	@GetMapping("/posts/category/{categoryId}")
	public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable Integer categoryId) {
		List<PostDTO> posts = this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
	}

	// Get post Containing the Keyword
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDTO>> searchByTitle(@PathVariable String keyword) {
		List<PostDTO> result = this.postService.searchPostByTitle(keyword);
		return new ResponseEntity<List<PostDTO>>(result, HttpStatus.OK);
	}

	// Get All Posts
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getALlPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
		// return ResponseEntity.ok(this.postService.getAllPost());
		PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}

	// Update Post Title
	@PutMapping("/post/{postId}/title")
	public ResponseEntity<PostDTO> updatePostTitle(@PathVariable("postId") Integer postId, @RequestParam String title) {
		PostDTO updatedPost = this.postService.updatePostTitle(postId, title);
		return new ResponseEntity<PostDTO>(updatedPost, HttpStatus.OK);
	}

	// Update Post Content
	@PutMapping("/post/{postId}/content")
	public ResponseEntity<PostDTO> updatePostContent(@PathVariable("postId") Integer postId,
			@RequestParam String content) {
		PostDTO updatedPost = this.postService.updatePostContent(postId, content);
		return new ResponseEntity<PostDTO>(updatedPost, HttpStatus.OK);
	}

	// Update Post Title
	@PutMapping("/post/{postId}/category/{categoryId}")
	public ResponseEntity<PostDTO> updatePostCategory(@PathVariable("postId") Integer postId,
			@PathVariable("categoryId") Integer categoryId) {
		PostDTO updatedPost = this.postService.updatePostCategory(postId, categoryId);
		return new ResponseEntity<PostDTO>(updatedPost, HttpStatus.OK);
	}

	// Delete
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ResponseEntity<>(new ApiResponse("Post Deleted!!", true), HttpStatus.OK);
	}

	@GetMapping("/posts/download")
	public ResponseEntity<ApiResponse> downloadToExcel(HttpServletResponse response) throws Exception {
		this.postService.downloadToExcel(response);
		return new ResponseEntity<ApiResponse>(new ApiResponse("File Downloaded!!", true), HttpStatus.OK);
	}
}