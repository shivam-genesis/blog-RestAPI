package com.blog.services;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.blog.configPost.PostResponse;
import com.blog.payloads.PostDTO;

public interface PostService {
	// Create Post
	PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);

	// Get Post By Id
	PostDTO getPostById(Integer postId);

	// Get All Post By User
	List<PostDTO> getPostsByUser(Integer userId);

	// Get All Post By Category
	List<PostDTO> getPostsByCategory(Integer categoryId);

	// Get posts containing the keyword;
	List<PostDTO> searchPostByTitle(String keyword);

	// Get All Post
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	// Update Post Title
	PostDTO updatePostTitle(Integer postId, String title);

	// Update Post Content
	PostDTO updatePostContent(Integer postId, String content);

	// Update Post Category
	PostDTO updatePostCategory(Integer postId, Integer categoryId);

	// Delete Post
	void deletePost(Integer postId);
	
	void downloadToExcel(HttpServletResponse response) throws Exception ;
}
