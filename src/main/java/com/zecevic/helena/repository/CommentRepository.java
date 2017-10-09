package com.zecevic.helena.repository;

import com.zecevic.helena.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAll();

    Comment findById(long id);
}
