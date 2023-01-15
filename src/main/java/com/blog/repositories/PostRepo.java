package com.blog.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

	Page<Post> findAll(Pageable p);

	List<Post> findByPostUser(User user);

	List<Post> findByPostCategory(Category category);
	
	//@Query("select p from post where p.title like=:k")
	//List<Post> searchByTitle(@Param("k") String title);
	
	List<Post> findByPostTitleContaining(String title);
}
