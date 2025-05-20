package com.onurege.demo.Service;

import com.onurege.demo.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TmdbService tmdbService;

    public UserService(UserRepository userRepository, TmdbService tmdbService){

        this.userRepository = userRepository;
        this.tmdbService = tmdbService;
    }

    public boolean rateMovie(String userId, Integer tmdbId, String imdbId, String title, Integer rating) {
        imdbId = tmdbService.fetchImdbIdFromTmdb(tmdbId);
        Optional<String> result = userRepository.createRatedRelation(userId, tmdbId, imdbId, title, rating);
        return result.isPresent();
    }

    public Integer getRating(String userId, Integer tmdbId) {
        return userRepository.findUserRating(userId, tmdbId);
    }
//
//    public void createUser(String email, String password, String name) {
//        userRepository.save(email,password,name);
//    }
}
