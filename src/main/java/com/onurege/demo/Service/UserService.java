package com.onurege.demo.Service;

import com.onurege.demo.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public boolean rateMovie(int userId, Integer tmdbId, String imdbId, String title, Integer rating) {
        Optional<String> result = userRepository.createRatedRelation(userId, tmdbId, imdbId, title, rating);
        return result.isPresent();
    }

    public Integer getRating(Integer userId, Integer tmdbId) {
        return userRepository.findUserRating(userId, tmdbId);
    }
}
