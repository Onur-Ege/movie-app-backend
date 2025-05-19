package com.onurege.demo.Repository;

import com.onurege.demo.data.MovieNode;
import com.onurege.demo.data.movie.model.MovieDto;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends Neo4jRepository<MovieNode, Long> {

    @Query("""
        MATCH (:User {userId: $userId})-[r:RATED]->(m:Movie)
        WHERE r.rating = 5
        RETURN m.imdbId AS imdbId LIMIT 20
    """)
    List<String> findImdbIdsOfFavMovies(@Param("userId") Long userId);

}