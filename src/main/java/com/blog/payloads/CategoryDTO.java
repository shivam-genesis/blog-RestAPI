package com.blog.payloads;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

	private Integer categoryId;
	
	@NotEmpty
	@Size(min=4,message="Title must be atleast length 4")
	private String categoryTitle;
	
	@NotEmpty
	@Size(min=20,message="Description must be atleast of length 20")
	private String categoryDescription;
	
	@JsonManagedReference
	private List<PostDTO> categoryPosts = new ArrayList<>();
}
