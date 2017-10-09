package com.zecevic.helena.controller;

import com.zecevic.helena.model.ProgrammingLanguage;
import com.zecevic.helena.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/languages")
public class LanguageController {

    private final LanguageService languageService;

    @Autowired
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping
    public ResponseEntity getAllLanguages() {
        final List<ProgrammingLanguage> languages = languageService.findAll();
        return new ResponseEntity<>(languages, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findLanguageById(@PathVariable long id) {
        final ProgrammingLanguage language = languageService.findById(id);
        if (language == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(language, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity addLanguage(@RequestBody ProgrammingLanguage language) {
        final ProgrammingLanguage createdLanguage = languageService.addLanguage(language);
        if (createdLanguage == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdLanguage.getId(), HttpStatus.OK);
    }
}
