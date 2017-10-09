package com.zecevic.helena.controller;

import com.zecevic.helena.model.Comment;
import com.zecevic.helena.model.Snippet;
import com.zecevic.helena.model.Vote;
import com.zecevic.helena.model.dto.FilterDTO;
import com.zecevic.helena.service.SnippetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/snippets")
public class SnippetController {

    private final SnippetService snippetService;

    @Autowired
    public SnippetController(SnippetService snippetService) {
        this.snippetService = snippetService;
    }

    @GetMapping
    public ResponseEntity getAllSnippets() {
        List<Snippet> snippets = snippetService.findAll();
        return new ResponseEntity<>(snippets, HttpStatus.OK);
    }

    @GetMapping("/{id}/{popularity}")
    public ResponseEntity findSnippetById(@PathVariable long id, @PathVariable boolean popularity) {
        final Snippet snippet = snippetService.findById(id, popularity);
        if (snippet == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(snippet, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addSnippet(@RequestBody Snippet snippet) {
        final Snippet createdSnippet = snippetService.addSnippet(snippet);
        if (createdSnippet == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdSnippet.getId(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteSnippet(@PathVariable long id) {
        if (!snippetService.deleteSnippet(id)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/block/{blocked}")
    public ResponseEntity blockSnippet(@PathVariable long id, @PathVariable boolean blocked) {
        final Snippet snippet = snippetService.blockSnippet(id, blocked);
        if (snippet == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(snippet, HttpStatus.OK);
    }

    @PostMapping("/filter")
    public ResponseEntity getFilteredSnippets(@RequestBody FilterDTO filterDTO) {
        final List<Snippet> filteredSnippets = snippetService.findFilteredSnippets(filterDTO);
        return new ResponseEntity<>(filteredSnippets, HttpStatus.OK);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity addComment(@PathVariable long id, @RequestBody Comment comment) {
        final Comment newComment = snippetService.addCommentToSnippet(id, comment);
        if (newComment == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(newComment, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}/comments/{commentId}")
    public ResponseEntity rateComment(@PathVariable long id, @PathVariable long commentId, @RequestBody Vote vote) {
        Vote newVote = snippetService.rateComment(id, commentId, vote);
        if (newVote == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(newVote, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}/comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable long id, @PathVariable long commentId) {
        final Comment deletedComment = snippetService.deleteComment(id, commentId);
        if (deletedComment == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(deletedComment.getId(), HttpStatus.OK);
    }
}
