package com.teamsapp.springboot.TeamsApp.dao;

import com.teamsapp.springboot.TeamsApp.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GameDAO extends JpaRepository<Game,Integer> {

    @Query("SELECT g FROM Game g WHERE g.round.id = :roundId")
    public Game findByRoundId(@Param("roundId") int roundId);

    @Query("SELECT g FROM Game g WHERE g.team1.id = :team1Id AND g.team2.id= :team2Id")
    public Game findByTeamsIdsG1(int team1Id,int team2Id);

    @Query("SELECT g FROM Game g WHERE g.team2.id = :team2Id AND g.team1.id= :team1Id")
    public Game findByTeamsIdsG2(int team2Id,int team1Id);

    @Query("SELECT g FROM Game g WHERE (g.team2.id = :team2Id AND g.team1.id= :team1Id OR g.team2.id = :team1Id AND g.team1.id= :team2Id) AND g.winner =:team1Id")
    public List<Game> getWinsOfTeam1VsTeam2(int team1Id, int team2Id);




}