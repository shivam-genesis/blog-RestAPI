package com.blog.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer commentId;

	private String commentContent;

	@ManyToOne
	@JoinColumn(name="userId")
	private User user;

	@ManyToOne
	@JoinColumn(name="postId")
	private Post post;
}