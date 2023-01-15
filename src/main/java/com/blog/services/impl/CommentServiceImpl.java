package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.configComment.CommentsDownload;
import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CommentDTO;
import com.blog.repositories.CommentRepo;
import com.blog.repositories.PostRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDTO createComment(CommentDTO commentDTO, Integer postId, Integer userId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post ", "Id: ", postId));
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User ", "Id: ", userId));
		Comment comment = this.modelMapper.map(commentDTO, Comment.class);
		comment.setPost(post);
		comment.setUser(user);
		Comment savedComment = this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment, CommentDTO.class);
	}

	@Override
	public CommentDTO getCommentById(Integer commentId) {
		Comment c = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "Id: ", commentId));
		return this.modelMapper.map(c, CommentDTO.class);
	}

	@Override
	public List<CommentDTO> getCommentsByUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User ", "Id: ", userId));
		List<Comment> comments = this.commentRepo.findByUser(user);
		List<CommentDTO> cDTOS = comments.stream().map(c -> this.modelMapper.map(c, CommentDTO.class))
				.collect(Collectors.toList());
		return cDTOS;
	}

	@Override
	public List<CommentDTO> getCommentsByPost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post ", "Id: ", postId));
		List<Comment> comments = this.commentRepo.findByPost(post);
		List<CommentDTO> cDTOS = comments.stream().map(c -> this.modelMapper.map(c, CommentDTO.class))
				.collect(Collectors.toList());
		return cDTOS;
	}

	@Override
	public List<CommentDTO> getAllComments() {
		List<Comment> comments = this.commentRepo.findAll();
		List<CommentDTO> cDTOS = comments.stream().map(c -> this.modelMapper.map(c, CommentDTO.class))
				.collect(Collectors.toList());
		return cDTOS;
	}

	@Override
	public CommentDTO updateCommentContent(Integer commentId, String content) {
		Comment c = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "Id: ", commentId));
		c.setCommentContent(content);
		Comment updatedComment = this.commentRepo.save(c);
		return this.modelMapper.map(updatedComment, CommentDTO.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment ", "Id: ", commentId));
		this.commentRepo.delete(comment);
	}
	
	@Override
	public void downloadToExcel(HttpServletResponse response) throws Exception{
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=comments.xlxs";
		response.setHeader(headerKey, headerValue);
		
		List<Comment> comments = this.commentRepo.findAll();
		if(comments.isEmpty()) {
			throw new ResourceNotFoundException("Comments");
		}
		CommentsDownload commentsDownload = new CommentsDownload(comments);
		commentsDownload.export(response);
	}
}

