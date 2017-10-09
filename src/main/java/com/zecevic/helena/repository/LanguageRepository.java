package com.zecevic.helena.repository;

import com.zecevic.helena.model.ProgrammingLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageRepository extends JpaRepository<ProgrammingLanguage, Long> {

    List<ProgrammingLanguage> findAll();

    ProgrammingLanguage findById(long id);

    ProgrammingLanguage findByName(String name);
}
