package com.zecevic.helena.controller;

import com.zecevic.helena.model.User;
import com.zecevic.helena.model.dto.SigninDTO;
import com.zecevic.helena.service.AuthenticationService;
import com.zecevic.helena.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody User user) {
        final User createdUser = authenticationService.addNewUser(user);
        if (createdUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdUser.getId(), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody SigninDTO signinDTO) {
        final User user = userService.findByUsernameAndPassword(signinDTO.getUsername(), signinDTO.getPassword());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        final Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        final Authentication authentication = new PreAuthenticatedAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/signout")
    public ResponseEntity signout() {
        SecurityContextHolder.clearContext();
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/auth")
    public ResponseEntity auth() {
        final User currentUser = authenticationService.getCurrentUser();
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }
}
