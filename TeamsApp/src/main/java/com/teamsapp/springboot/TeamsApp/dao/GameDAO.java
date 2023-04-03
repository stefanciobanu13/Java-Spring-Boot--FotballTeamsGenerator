package com.teamsapp.springboot.TeamsApp.dao;

import com.teamsapp.springboot.TeamsApp.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface GameDAO extends JpaRepository<Game,Integer> {





    @Query("SELECT g FROM Game g WHERE g.round.id = :roundId")
    public Game findByRoundId(@Param("roundId") int roundId);


    @Query("SELECT g FROM Game g WHERE g.team1.id = :team1Id AND g.team2.id= :team2Id")
    public Game findByTeamsIds(int team1Id,int team2Id);







}