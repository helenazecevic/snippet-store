package com.zecevic.helena.service;

import com.zecevic.helena.model.User;
import com.zecevic.helena.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final ServletContext servletContext;

    @Autowired
    public UserService(UserRepository userRepository, AuthenticationService authenticationService, ServletContext servletContext) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
        this.servletContext = servletContext;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(long id) {
        return userRepository.findById(id);
    }

    public User findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> filterUsers(String username) {
        List<User> users = userRepository.findAll();
        List<User> filteredUsers = new ArrayList<>();
        for (User user : users) {
            if (user.getUsername().toLowerCase().contains(username)) {
                filteredUsers.add(user);
            }
        }
        return filteredUsers;
    }

    public String uploadPhoto(MultipartFile file) {
        try {
            String uuid = UUID.randomUUID().toString();
            final String imageName = file.getOriginalFilename();
            final String imageAbsoluteUrl = servletContext.getRealPath("/img") + "/" + uuid + "_" + imageName;
            final String imageRelativeUrl = "/img/" + uuid + "_" + imageName;

            final File newImage = new File(imageAbsoluteUrl);
            if (!newImage.exists()) {
                newImage.createNewFile();
            }
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(newImage));
            stream.write(file.getBytes());
            stream.close();
            return imageRelativeUrl;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
