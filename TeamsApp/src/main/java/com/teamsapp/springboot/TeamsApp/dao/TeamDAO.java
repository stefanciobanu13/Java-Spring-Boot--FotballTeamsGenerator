package com.teamsapp.springboot.TeamsApp.dao;


import com.teamsapp.springboot.TeamsApp.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;


@Repository
public interface TeamDAO extends JpaRepository<Team, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Team t SET t.points = :points WHERE t.id = :teamId")
    void updatePointsById(int teamId, int points);


}



