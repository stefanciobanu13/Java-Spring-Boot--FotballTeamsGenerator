package com.teamsapp.springboot.TeamsApp.service;

import com.teamsapp.springboot.TeamsApp.entity.Game;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GameService {


    public void saveGame(Game game);

    public List<Game> getGames();

    public Game getGame(int theId);

    public void deleteGame(int theId);

    public Game findByRoundId(int roundId);

    public Game findByTeamsIdsG1(int team1Id, int team2Id);

    public Game findByTeamsIdsG2(int team1Id, int team2Id);

    public List<Game> getWinsOfTeam1VsTeam2(int team1Id, int team2Id);



}
