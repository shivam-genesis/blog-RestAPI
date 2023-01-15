package com.blog.payloads;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

	private Integer commentId;

	private String commentContent;

	@JsonBackReference
	private UserDTO user;
	
	@JsonBackReference
	private PostDTO post;
}
