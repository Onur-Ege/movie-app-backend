package com.onurege.demo.Repository;

import com.onurege.demo.data.MovieNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends Neo4jRepository<MovieNode, Long> {

    @Query("""
        MATCH (u:User {userId: $userId})
        MERGE (m:Movie {tmdbId: $tmdbId})
        ON CREATE SET
            m.imdbId = $imdbId,
            m.title = $title
        MERGE (u)-[r:RATED]->(m)
        SET r.rating = 5
        RETURN 'success' AS result
    """)
    Optional<String> createRatedRelation(
            @Param("userId") int userId,
            @Param("tmdbId") Integer tmdbId,
            @Param("imdbId") String imdbId,
            @Param("title") String title
            );
}
