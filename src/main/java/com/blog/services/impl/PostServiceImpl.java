package com.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.configPost.PostResponse;
import com.blog.configPost.PostsDownload;
import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.PostDTO;
import com.blog.repositories.CategoryRepo;
import com.blog.repositories.PostRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User ", "Id: ", userId));
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category ", "Id: ", categoryId));
		Post post = this.modelMapper.map(postDTO, Post.class);
		post.setPostDate(new Date());
		post.setPostUser(user);
		post.setPostCategory(category);
		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDTO.class);
	}

	@Override
	public PostDTO getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post ", "Id: ", postId));
		return this.modelMapper.map(post, PostDTO.class);
	}

	@Override
	public List<PostDTO> getPostsByUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User ", "Id: ", userId));
		List<Post> posts = this.postRepo.findByPostUser(user);
		List<PostDTO> postDTOS = posts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		return postDTOS;
	}

	@Override
	public List<PostDTO> getPostsByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category ", "Id: ", categoryId));
		List<Post> posts = this.postRepo.findByPostCategory(category);
		List<PostDTO> postDTOS = posts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		return postDTOS;
	}

	@Override
	public List<PostDTO> searchPostByTitle(String keyword) {
		List<Post> posts = this.postRepo.findByPostTitleContaining(keyword);
		List<PostDTO> postDTOS = posts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		return postDTOS;
	}

	@Transactional
	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		Sort sort = null;
		if (sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		} else {
			sort = Sort.by(sortBy).descending();
		}
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post> allPosts = pagePost.getContent();
		// List<Post> allPosts = this.postRepo.findAll(); //Before Pagination
		List<PostDTO> allPostDTOS = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(allPostDTOS);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDTO updatePostTitle(Integer postId, String title) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post ", " Id: ", postId));
		post.setPostTitle(title);
		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDTO.class);
	}

	@Override
	public PostDTO updatePostContent(Integer postId, String content) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post ", " Id: ", postId));
		post.setContent(content);
		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDTO.class);
	}

	@Override
	public PostDTO updatePostCategory(Integer postId, Integer categoryId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post ", " Id: ", postId));
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category ", "Id: ", categoryId));
		post.setPostCategory(category);
		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDTO.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post ", "Id: ", postId));
		this.postRepo.delete(post);
	}
	
	@Override
	public void downloadToExcel(HttpServletResponse response) throws Exception{
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=posts.xlxs";
		response.setHeader(headerKey, headerValue);
		
		List<Post> posts = this.postRepo.findAll();
		if(posts.isEmpty()) {
			throw new ResourceNotFoundException("Posts");
		}
		PostsDownload postsDownload = new PostsDownload(posts);
		postsDownload.export(response);
	}

}