package com.teamsapp.springboot.TeamsApp.dao;


import com.teamsapp.springboot.TeamsApp.entity.SmallFinal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmallFinalDAO extends JpaRepository<SmallFinal,Integer> {




}
