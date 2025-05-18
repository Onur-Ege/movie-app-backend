package com.onurege.demo.Controller;

import com.onurege.demo.data.FavoriteRequest;
import com.onurege.demo.Service.UserService;
import com.onurege.demo.data.FavoriteResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/favorite")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<FavoriteResponse> markAsFavorite(@RequestBody FavoriteRequest request) {
        boolean success = userService.markAsFavorite(request.getUserId(), request.getTmdbId(),request.getImdbId(),request.getTitle());
        if (success) {
            return ResponseEntity.ok(new FavoriteResponse("✅ Favorite relation created with rating = 5", true));
        } else {
            return ResponseEntity.ok(new FavoriteResponse("❌ Movie or User not found in Neo4j", false));
        }
    }
}

