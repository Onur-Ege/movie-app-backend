package com.onurege.demo.Service;

import com.onurege.demo.Controller.UserController;
import com.onurege.demo.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public boolean markAsFavorite(int userId, Integer tmdbId, String imdbId, String title) {
        Optional<String> result = userRepository.createRatedRelation(userId, tmdbId, imdbId, title);
        return result.isPresent();
    }
}
