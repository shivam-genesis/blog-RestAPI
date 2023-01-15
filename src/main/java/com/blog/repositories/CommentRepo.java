package com.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.entities.User;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer> {
	public List<Comment> findByUser(User user);

	public List<Comment> findByPost(Post post);
}
