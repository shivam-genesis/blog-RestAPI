package com.blog.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post {

	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;

	private String postTitle;

	@Column(length = 10000)
	private String content;

	private Date postDate;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private List<Comment> postComments = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "category_Id")
	private Category postCategory;

	@ManyToOne
	@JoinColumn(name = "user_Id")
	private User postUser;
	
	@OneToOne
	@JoinColumn(name="post_image_Id")
	private PostImage postImage;

}