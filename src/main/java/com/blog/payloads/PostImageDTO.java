package com.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostImageDTO {

	private Integer postImageId;
	private String postImageName;
	private String postImageType;
	private String postImagePath;
}