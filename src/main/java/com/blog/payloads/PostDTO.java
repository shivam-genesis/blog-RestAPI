package com.blog.payloads;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

	private Integer postId;

	@NotEmpty
	@Size(min = 4, message = "Title must be of length 4")
	private String postTitle;

	@NotEmpty
	@Size(min = 10, max = 10000, message = "Content must be between 20-10000 characters")
	private String content;

	private Date postDate;

	@JsonManagedReference
	private List<CommentDTO> postComments = new ArrayList<>();

	@JsonBackReference
	private CategoryDTO postCategory;
	
	@JsonBackReference
	private UserDTO postUser;
	
	@JsonManagedReference
	private PostImageDTO postImage;
	
}
