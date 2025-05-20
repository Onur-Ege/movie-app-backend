package com.onurege.demo.Repository;

import com.onurege.demo.data.MovieNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends Neo4jRepository<MovieNode, String> {

    @Query("""
                MERGE (u:User {userId: $userId})
                MERGE (m:Movie {tmdbId: $tmdbId})
                ON CREATE SET
                    m.imdbId = $imdbId,
                    m.title = $title
                MERGE (u)-[r:RATED]->(m)
                SET r.rating = $rating
                RETURN 'success' AS result
            """)
    Optional<String> createRatedRelation(
            @Param("userId") String userId,
            @Param("tmdbId") Integer tmdbId,
            @Param("imdbId") String imdbId,
            @Param("title") String title,
            @Param("rating") Integer rating
    );

    @Query("""
                MATCH (u:User {userId: $userId})-[r:RATED]->(m:Movie {tmdbId: $tmdbId})
                RETURN r.rating
            """)
    Integer findUserRating(String userId, Integer tmdbId);
}
//    @Query("""
//        MERGE (u:User {email: $email})
//        ON CREATE SET
//            u.password = $password,
//            m.name = $name
//        RETURN 'success' AS result
//    """)
//    Optional<String> save(String email, String password, String name);
//}
