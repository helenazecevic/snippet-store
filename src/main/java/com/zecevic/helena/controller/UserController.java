package com.zecevic.helena.controller;

import com.zecevic.helena.model.User;
import com.zecevic.helena.service.AuthenticationService;
import com.zecevic.helena.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Autowired
    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public ResponseEntity getAllUsers() {
        final List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findUserById(@PathVariable long id) {
        final User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/block/{blocked}")
    public ResponseEntity blockUser(@PathVariable long id, @PathVariable boolean blocked) {
        final User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        user.setBlocked(blocked);
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/filter/{username}")
    public ResponseEntity filter(@PathVariable String username) {
        final List<User> filteredUsers = userService.filterUsers(username.toLowerCase());
        return new ResponseEntity<>(filteredUsers, HttpStatus.OK);
    }

    @PostMapping("/photo")
    public ResponseEntity upload(@RequestParam("photo") MultipartFile file) {
        final String path = userService.uploadPhoto(file);
        if (path == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        final User currentUser = authenticationService.getCurrentUser();
        currentUser.setPicture(path);
        userService.saveUser(currentUser);
        return new ResponseEntity<>("{\"photo\": \"" + path + "\"}", HttpStatus.OK);
    }
}
