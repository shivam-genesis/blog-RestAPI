package com.blog.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer categoryId;

	@Column(name = "title", length = 50, nullable = false)
	private String categoryTitle;

	@Column(name = "description", length = 500, nullable = false)
	private String categoryDescription;

	@OneToMany(mappedBy = "postCategory", cascade = CascadeType.ALL)
	private List<Post> categoryPosts = new ArrayList<>();

}