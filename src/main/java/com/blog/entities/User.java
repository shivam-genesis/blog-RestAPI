package com.blog.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(name = "user_id")
	private int userId;

	@Column(name = "user_name", nullable = false)
	private String name;

	@Column(name = "user_email", nullable = false)
	private String email;

	@Column(name = "user_password", nullable = false)
	private String password;

	private String encodedPassword;

	@Column(name = "about", nullable = false)
	private String about;

	@Column(name = "user_role", nullable = false)
	private String role;

	@OneToMany(mappedBy = "postUser", cascade = CascadeType.ALL)
	private List<Post> userPosts = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Comment> userComments = new ArrayList<>();

	@OneToOne
	@JoinColumn(name = "user_image_id")
	private UserImage userImage;
}