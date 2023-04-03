package com.teamsapp.springboot.TeamsApp.dao;

import com.teamsapp.springboot.TeamsApp.entity.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundDAO  extends JpaRepository<Round,Integer> {



    @Query("SELECT r FROM Round r WHERE r.number = :roundNumber")
    public Round findByRoundNumber(@Param("roundNumber") int roundNumber);





}
