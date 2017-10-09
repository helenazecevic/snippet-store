package com.zecevic.helena.service;

import com.zecevic.helena.model.ProgrammingLanguage;
import com.zecevic.helena.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    @Autowired
    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public List<ProgrammingLanguage> findAll() {
        return languageRepository.findAll();
    }

    public ProgrammingLanguage findById(long id) {
        return languageRepository.findById(id);
    }

    public ProgrammingLanguage findByName(String name) {
        return languageRepository.findByName(name);
    }

    public ProgrammingLanguage addLanguage(ProgrammingLanguage newLanguage) {
        final List<ProgrammingLanguage> languages = languageRepository.findAll();
        for (ProgrammingLanguage language : languages) {
            if (language.getName().toLowerCase().equals(newLanguage.getName().toLowerCase())) {
                return null;
            }
        }
        return languageRepository.save(newLanguage);
    }
}
