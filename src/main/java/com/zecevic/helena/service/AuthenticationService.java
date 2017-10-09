package com.zecevic.helena.service;

import com.zecevic.helena.model.Role;
import com.zecevic.helena.model.User;
import com.zecevic.helena.repository.AddressRepository;
import com.zecevic.helena.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public AuthenticationService(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    public User addNewUser(User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return null;
        }
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return null;
        }
        user.setRole(Role.REGULAR);
        addressRepository.save(user.getAddress());
        return userRepository.save(user);
    }

    public User getCurrentUser() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            return (User) auth.getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}
