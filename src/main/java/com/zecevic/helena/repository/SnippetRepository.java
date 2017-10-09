package com.zecevic.helena.repository;

import com.zecevic.helena.model.Snippet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SnippetRepository extends JpaRepository<Snippet, Long> {

    List<Snippet> findAll();

    Snippet findById(long id);

}
