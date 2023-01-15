package com.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.entities.PostImage;

@Repository
public interface PostImageRepo extends JpaRepository<PostImage,Integer> {
}
