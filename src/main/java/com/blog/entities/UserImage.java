package com.blog.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class UserImage {
	@Id
	private String userImageId;
	private String userImageName;
	private String userImageType;
	private String userImagePath;
	
	@OneToOne(mappedBy = "userImage", cascade = CascadeType.ALL)
	private User user;
}
