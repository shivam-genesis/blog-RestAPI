package com.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.entities.UserImage;

@Repository
public interface UserImageRepo extends JpaRepository<UserImage, String> {
}
