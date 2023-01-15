package com.blog.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class PostImage {

	@Id
	private Integer postImageId;
	private String postImageName;
	private String postImageType;
	private String postImagePath;

	@OneToOne(mappedBy = "postImage", cascade = CascadeType.ALL)
	private Post Post;
}
