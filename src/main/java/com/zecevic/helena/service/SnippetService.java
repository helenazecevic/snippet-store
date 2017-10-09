package com.zecevic.helena.service;

import com.zecevic.helena.model.*;
import com.zecevic.helena.model.dto.FilterDTO;
import com.zecevic.helena.repository.CommentRepository;
import com.zecevic.helena.repository.RatingRepository;
import com.zecevic.helena.repository.SnippetRepository;
import com.zecevic.helena.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SnippetService {

    private final SnippetRepository snippetRepository;
    private final AuthenticationService authenticationService;
    private final CommentRepository commentRepository;
    private final VoteRepository voteRepository;
    private final RatingRepository ratingRepository;

    @Autowired
    public SnippetService(SnippetRepository snippetRepository, AuthenticationService authenticationService,
                          CommentRepository commentRepository, VoteRepository voteRepository, RatingRepository ratingRepository) {
        this.snippetRepository = snippetRepository;
        this.authenticationService = authenticationService;
        this.commentRepository = commentRepository;
        this.voteRepository = voteRepository;
        this.ratingRepository = ratingRepository;
    }

    public List<Snippet> findAll() {
        return snippetRepository.findAll();
    }

    public Snippet findById(long id, boolean popularity) {
        Snippet snippet = snippetRepository.findById(id);
        if (snippet == null) {
            return null;
        }

        if (snippet.getTimeRemaining() > 0) {
            final Date expireDate = new Date(snippet.getDate().getTime() + snippet.getTimeRemaining());
            if (expireDate.before(new Date())) {
                snippetRepository.delete(snippet.getId());
                return null;
            }
        }

        if (popularity) {
            Collections.sort(snippet.getComments(), new Comparator<Comment>() {
                @Override
                public int compare(Comment o1, Comment o2) {
                    int overall1 = o1.getRating().getPositive() - o1.getRating().getNegative();
                    int overall2 = o2.getRating().getPositive() - o2.getRating().getNegative();
                    return overall1 > overall2 ? 1 : 0;
                }
            });
        }

        Collections.sort(snippet.getComments(), new Comparator<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
                return o1.getDate().after(o2.getDate()) ? 1 : 0;
            }
        });

        return snippet;
    }

    public Snippet addSnippet(Snippet snippet) {
        snippet.setDate(new Date());

        final User currentUser = authenticationService.getCurrentUser();
        if (currentUser != null && currentUser.isBlocked()) {
            return null;
        }
        snippet.setUser(currentUser);
        final Snippet createdSnippet = snippetRepository.save(snippet);

        if (createdSnippet != null && createdSnippet.getTimeRemaining() > 0) {
            final Thread thread = new Thread() {
                public void run() {
                    try {
                        Thread.sleep(createdSnippet.getTimeRemaining());
                        snippetRepository.delete(createdSnippet.getId());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        }
        return createdSnippet;
    }

    public boolean deleteSnippet(long id) {
        final Snippet snippet = snippetRepository.findById(id);
        if (snippet == null) {
            return true;
        }
        final User currentUser = authenticationService.getCurrentUser();
        if (currentUser.getRole() != Role.ADMIN && snippet.getUser() != null && currentUser.getId() != snippet.getUser().getId()) {
            return false;
        }
        snippetRepository.delete(id);
        return true;
    }

    public Snippet blockSnippet(long id, boolean blocked) {
        final Snippet snippet = snippetRepository.findById(id);
        if (snippet == null) {
            return null;
        }
        snippet.setBlocked(blocked);
        return snippetRepository.save(snippet);
    }

    public List<Snippet> findFilteredSnippets(FilterDTO filterDTO) {
        List<Snippet> snippets = snippetRepository.findAll();
        List<Snippet> filteredSnippets = new ArrayList<>();

        for (Snippet snippet : snippets) {
            if (filterDTO.getDescription() != null && !snippet.getDescription().toLowerCase().contains(filterDTO.getDescription().toLowerCase())) {
                continue;
            }

            if (filterDTO.getLanguage() != null && !snippet.getLanguage().getName().equals(filterDTO.getLanguage().getName())) {
                continue;
            }

            if (filterDTO.getStartDate() != null && !snippet.getDate().after(filterDTO.getStartDate())) {
                continue;
            }

            if (filterDTO.getEndDate() != null && !snippet.getDate().before(filterDTO.getEndDate())) {
                continue;
            }
            filteredSnippets.add(snippet);
        }
        return filteredSnippets;
    }

    public Comment addCommentToSnippet(long id, Comment comment) {
        final Snippet snippet = snippetRepository.findById(id);
        final User currentUser = authenticationService.getCurrentUser();

        if (snippet == null || snippet.isBlocked()) {
            return null;
        }

        if (currentUser != null) {
            if (currentUser.isBlocked()) {
                return null;
            }
        }
        comment.setUser(currentUser);
        comment.setDate(new Date());
        comment.setSnippet(snippet);
        ratingRepository.save(comment.getRating());
        return commentRepository.save(comment);
    }

    public Vote rateComment(long id, long commentId, Vote vote) {
        final Snippet snippet = snippetRepository.findById(id);
        final User currentUser = authenticationService.getCurrentUser();
        final Comment comment = commentRepository.findById(commentId);

        if (snippet == null || currentUser.isBlocked()) {
            return null;
        }

        for (Vote v : comment.getRating().getVotes()) {
            if (v.getUser().getId() == currentUser.getId()) {
                return null;
            }
        }
        vote.setUser(currentUser);
        vote.setRating(comment.getRating());
        voteRepository.save(vote);

        if (vote.getStatus() == VoteStatus.UPVOTE) {
            comment.getRating().setPositive(comment.getRating().getPositive() + 1);
        } else {
            comment.getRating().setNegative(comment.getRating().getNegative() + 1);
        }
        commentRepository.save(comment);
        return vote;
    }

    public Comment deleteComment(long id, long commentId) {
        final User currentUser = authenticationService.getCurrentUser();
        final Comment comment = commentRepository.findById(commentId);

        if (currentUser.getRole() != Role.ADMIN && comment.getUser() != null && currentUser.getId() != comment.getUser().getId()) {
            return null;
        }
        commentRepository.delete(commentId);
        return comment;
    }
}
