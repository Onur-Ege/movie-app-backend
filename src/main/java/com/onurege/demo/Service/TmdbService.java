package com.onurege.demo.Service;


import com.onurege.demo.data.detail.dto.MovieDetailDto;
import com.onurege.demo.data.detail.mapper.MovieDetailMapper;
import com.onurege.demo.data.detail.model.MovieDetail;
import com.onurege.demo.data.movie.model.MovieDto;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class TmdbService {

    private final RestTemplate restTemplate;
    private final MovieDetailMapper mapper;


    private static final String API_KEY = "2d7afcc644de4bba5e31069f9fd56e11";  // replace this
    private static final String BASE_URL = "https://api.themoviedb.org/3";

    @Autowired
    public TmdbService(RestTemplate restTemplate, MovieDetailMapper mapper) {

        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }

    public List<MovieDto> getPopularMovies() {
        String url = BASE_URL + "/trending/movie/day?api_key=" + API_KEY;
        return fetchMovieList(url);
    }

    public List<MovieDto> getDiscoverMovies() {
        String url = BASE_URL + "/discover/movie?api_key=" + API_KEY;
        return fetchMovieList(url);
    }

    public MovieDetail fetchMovieDetail(int movieId) {
        String url = String.format(
                "https://api.themoviedb.org/3/movie/%d?api_key=%s&append_to_response=credits,reviews",
                movieId, API_KEY
        );

        MovieDetailDto dto = restTemplate.getForObject(url, MovieDetailDto.class);
        return mapper.map(dto);
    }

    public MovieDto getMovieByImdbId(String imdbId){
        String url = String.format("%s/find/%s?api_key=%s&external_source=imdb_id",BASE_URL,imdbId, API_KEY);

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        List<Map<String, Object>> movieResults = (List<Map<String, Object>>) response.get("movie_results");

        if (movieResults == null || movieResults.isEmpty()) return null;

        Map<String,Object> movie = movieResults.get(0);
        return mapToDto(movie);
    }

    private List<MovieDto> fetchMovieList(String url) {
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");

        if (results == null) return List.of();

        return results.stream()
                .map(this::mapToDto)
                .toList();
    }

    private MovieDto mapToDto(Map<String, Object> movie) {
        return new MovieDto(
                (String) movie.get("backdrop_path"),
                ((List<?>) movie.get("genre_ids")).stream().map(Object::toString).toList(),
                (Integer) movie.get("id"),
                (String) movie.get("original_language"),
                (String) movie.get("original_title"),
                (String) movie.get("overview"),
                movie.get("popularity") != null ? ((Number) movie.get("popularity")).doubleValue() : null,
                (String) movie.get("poster_path"),
                (String) movie.get("release_date"),
                (String) movie.get("title"),
                movie.get("vote_average") != null ? ((Number) movie.get("vote_average")).doubleValue() : null,
                (Integer) movie.get("vote_count"),
                (Boolean) movie.get("video")
        );
    }
    private List<String> convertGenreIds(Object genreIdsObj) {
        if (genreIdsObj instanceof List<?> genreIdsRaw) {
            return genreIdsRaw.stream()
                    .map(Object::toString)
                    .toList();
        }
        return List.of();
    }
}