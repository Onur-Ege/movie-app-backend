package com.onurege.demo.Controller;

import com.onurege.demo.Service.TmdbService;
import com.onurege.demo.data.detail.model.MovieDetail;
import com.onurege.demo.data.movie.model.MovieDto;
import com.onurege.demo.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;
    private final TmdbService tmdbService;

    @Autowired
    public MovieController(MovieService movieService, TmdbService tmdbService){
        this.movieService = movieService;
        this.tmdbService = tmdbService;
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<MovieDto>> getRatedMovies(@RequestParam Long userId) {
        List<MovieDto> movies = movieService.getFavMovies(userId);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<MovieDto>> getPopularMovies(){
        List<MovieDto> movies = tmdbService.getPopularMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/discover")
    public ResponseEntity<List<MovieDto>> getDiscoverMovies(){
        List<MovieDto> movies = tmdbService.getDiscoverMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDetail> getMovieDetail(@PathVariable int movieId) {
        MovieDetail response = tmdbService.fetchMovieDetail(movieId);
        return ResponseEntity.ok(response);
    }
}
