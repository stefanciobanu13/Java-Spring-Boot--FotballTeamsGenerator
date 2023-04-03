package com.teamsapp.springboot.TeamsApp.dao;

import com.teamsapp.springboot.TeamsApp.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Cacheable;
import java.util.List;

@Repository
public interface GoalDAO extends JpaRepository<Goal,Integer> {

// querry that retrieves all the goals of a team, based on id

    @Query("SELECT g FROM Goal g WHERE g.team.id = :teamId")
    public List<Goal> getTeamGoals(@Param("teamId") int teamId);

//querry that retrieves all the goals that a team received

    @Query("SELECT g FROM Goal g INNER JOIN Game gm ON g.game.id= gm.id WHERE gm.team1.id = :teamId OR gm.team2.id = :teamId AND g.team.id <> :teamId")
    public List<Goal> getReceivedGoals(@Param("teamId") int teamId);

    //this querry retrieves all the goals from the matches between two teams
    @Query("SELECT g FROM Goal g INNER JOIN Game gm ON g.team.id = gm.team1.id OR g.team.id = gm.team2.id WHERE gm.team1.id = :team1Id AND gm.team2.id = :team2Id")
    List<Goal> findGoalsBetweenTeams(@Param("team1Id") int team1Id, @Param("team2Id") int team2Id);



}
