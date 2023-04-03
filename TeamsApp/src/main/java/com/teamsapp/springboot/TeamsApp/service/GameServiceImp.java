package com.teamsapp.springboot.TeamsApp.service;


import com.teamsapp.springboot.TeamsApp.dao.GameDAO;
import com.teamsapp.springboot.TeamsApp.entity.Game;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImp implements GameService{

        private GameDAO gameDAO;

        public GameServiceImp(GameDAO gameDAO){
            this.gameDAO = gameDAO;
        }


    @Override
    public void saveGame(Game game) {
                gameDAO.save(game);
    }

    @Override
    public List<Game> getGames() {
        return gameDAO.findAll();
    }

    @Override
    public Game getGame(int theId) {

            Optional<Game> optionalGame = gameDAO.findById(theId);
            Game theGame = optionalGame.get();

        return theGame;
    }

    @Override
    public void deleteGame(int theId) {
            gameDAO.deleteById(theId);
    }

    @Override
    public Game findByRoundId(int roundId) {

            return gameDAO.findByRoundId(roundId);
    }

    @Override
    public Game findByTeamsIds(int team1Id, int team2Id) {
        return gameDAO.findByTeamsIds(team1Id,team2Id);
    }


}
