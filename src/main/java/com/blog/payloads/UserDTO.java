package com.blog.payloads;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private int userId;

	@NotEmpty
	@Size(min = 4, message = "Name must be atleast of length 4!")
	private String name;

	@Email(message = "Enter Valid Email!")
	private String email;

	@JsonProperty(access = Access.WRITE_ONLY) // I used it to hide password in response
	@NotEmpty
	@Size(min = 5, max = 15, message = "Password must be between 5-15 length!")
	// @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$")
	private String password;

	@JsonIgnore
	private String encodedPassword;

	@NotEmpty
	private String about;

	@NotEmpty
	private String role;

	@JsonManagedReference
	private List<PostDTO> userPosts = new ArrayList<>();

	@JsonManagedReference
	private List<CommentDTO> userComments = new ArrayList<>();

	@JsonManagedReference
	private UserImageDTO userImage;

}