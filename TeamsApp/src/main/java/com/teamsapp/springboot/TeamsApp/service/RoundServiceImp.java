package com.teamsapp.springboot.TeamsApp.service;

import com.teamsapp.springboot.TeamsApp.dao.RoundDAO;
import com.teamsapp.springboot.TeamsApp.entity.Round;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoundServiceImp implements RoundService{


    private RoundDAO roundDAO;

    public  RoundServiceImp(RoundDAO roundDAO){
        this.roundDAO = roundDAO;
    }

    @Override
    public void saveRound(Round round) {
            roundDAO.save(round);
    }

    @Override
    public List<Round> getRounds() {
        return roundDAO.findAll();
    }

    @Override
    public Round getRound(int theId) {

        Optional<Round> optionalRound = roundDAO.findById(theId);
        Round tempRound = optionalRound.get();

        return tempRound;
    }

    @Override
    public void deleteRound(int theId) {
        roundDAO.deleteById(theId);
    }

    @Override
    public Round findByRoundNumber(int roundNumber) {

        return roundDAO.findByRoundNumber(roundNumber);
    }


}
