package com.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserImageDTO {

	private String userImageId;
	private String userImageName;
	private String userImageType;
	private String userImagePath;
	
}
