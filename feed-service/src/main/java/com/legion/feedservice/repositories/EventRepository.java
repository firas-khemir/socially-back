package com.legion.feedservice.repositories;

import com.legion.feedservice.entities.event.Event;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;
import java.util.UUID;

public interface EventRepository extends Neo4jRepository<Event, UUID> {


    @Query("MATCH (follower:User {username: $followerUsername})-[:FOLLOWS]->(followee:User)-[:CREATED_BY]->(event:Event) " +
        "RETURN event ORDER BY interactionsCounter DESC")
    Set<Event> getEventsCreatedByFollowedUsers(String followerUsername);


    @Query("""
        MATCH (currentUser:User {uid: $currentUserId})
        MATCH (currentUser)-[:FOLLOW]->(followedUser:User)
        MATCH (events:Event)-[:CREATED_BY]->(followedUser)
        OPTIONAL MATCH (events)-[:ATTENDED]-(attendedUser:User)<-[:FOLLOW]-(currentUser)
        WITH events, followedUser, SIZE(collect(attendedUser)) AS interactionCount
        ORDER BY interactionCount DESC

        RETURN events""")
    Set<Event> findEventsCreatedByFollowedUsersOrderByInteractionCounter(@Param("currentUserId") String currentUserId);

}
