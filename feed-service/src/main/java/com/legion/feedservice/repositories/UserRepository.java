package com.legion.feedservice.repositories;

import com.legion.feedservice.entities.event.Event;
import com.legion.feedservice.entities.user.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRepository extends Neo4jRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    Optional<User> findByUid(String uid);

    @Query("""
        MATCH (follower:User {uid: $followerUid}), (followed:User {uid: $followedUid})
        CREATE (follower)-[r:FOLLOW]->(followed)
        RETURN COUNT(r) = 1 AS success
        """)
    Boolean followUser(String followerUid, String followedUid);

    @Query("""
        MATCH (cUser:User {uid: $0followerUid})-[r:FOLLOW]->(user:User {uid: $followedUid})
        DELETE r
        RETURN COUNT(r) = 0 AS success
        """)
    Boolean unfollowUser(String followerUid, String followedUid);


}
