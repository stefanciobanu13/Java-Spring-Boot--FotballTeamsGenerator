package com.teamsapp.springboot.TeamsApp.dao;


import com.teamsapp.springboot.TeamsApp.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlayerDAO extends JpaRepository<Player,Integer> {




}
