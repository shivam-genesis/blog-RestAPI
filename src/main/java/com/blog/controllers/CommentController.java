package com.blog.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

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

import com.blog.exceptions.ApiResponse;
import com.blog.payloads.CommentDTO;
import com.blog.services.CommentService;

@RestController
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping("/comment/user/{userId}/post/{postId}")
	public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO, @PathVariable Integer userId,
			@PathVariable Integer postId) {
		CommentDTO createdComment = this.commentService.createComment(commentDTO, postId, userId);
		return new ResponseEntity<CommentDTO>(createdComment, HttpStatus.CREATED);
	}

	@GetMapping("comment/{commentId}")
	public ResponseEntity<CommentDTO> getCommentById(@PathVariable("commentId") Integer commentId) {
		CommentDTO c = this.commentService.getCommentById(commentId);
		return new ResponseEntity<CommentDTO>(c, HttpStatus.OK);
	}

	@GetMapping("comments/user/{userId}")
	public ResponseEntity<List<CommentDTO>> getCommentByUser(@PathVariable("userId") Integer userId) {
		List<CommentDTO> c = this.commentService.getCommentsByUser(userId);
		return new ResponseEntity<List<CommentDTO>>(c, HttpStatus.OK);
	}

	@GetMapping("comments/post/{postId}")
	public ResponseEntity<List<CommentDTO>> getCommentByPost(@PathVariable("postId") Integer postId) {
		List<CommentDTO> c = this.commentService.getCommentsByPost(postId);
		return new ResponseEntity<List<CommentDTO>>(c, HttpStatus.OK);
	}

	@GetMapping("comments")
	public ResponseEntity<List<CommentDTO>> getAllComments() {
		List<CommentDTO> c = this.commentService.getAllComments();
		return new ResponseEntity<List<CommentDTO>>(c, HttpStatus.OK);
	}
	
	@PutMapping("/comment/{commentId}/content")
	public ResponseEntity<CommentDTO> updateCommentContent(@PathVariable("commentId") Integer commentId, @RequestParam String content){
		CommentDTO c = this.commentService.updateCommentContent(commentId, content);
		return new ResponseEntity<CommentDTO>(c,HttpStatus.OK);
	}

	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted!!", true), HttpStatus.OK);
	}

	@GetMapping("comments/download")
	public ResponseEntity<ApiResponse> downloadToExcel(HttpServletResponse response) throws Exception{
		this.commentService.downloadToExcel(response);
		return new ResponseEntity<ApiResponse>(new ApiResponse("File Downloaded!!",true),HttpStatus.OK);
	}
}
