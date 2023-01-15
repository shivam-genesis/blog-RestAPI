package com.blog.services;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.blog.payloads.CommentDTO;

public interface CommentService {
	
	CommentDTO createComment(CommentDTO commentDTO, Integer postId, Integer userId);
	
	CommentDTO getCommentById(Integer commentId);
	
	List<CommentDTO> getCommentsByUser(Integer userId);
	
	List<CommentDTO> getCommentsByPost(Integer postId);
	
	List<CommentDTO> getAllComments();
	
	CommentDTO updateCommentContent(Integer commentId, String content);

	void deleteComment(Integer commentId);
	
	void downloadToExcel(HttpServletResponse response) throws Exception; 
}
