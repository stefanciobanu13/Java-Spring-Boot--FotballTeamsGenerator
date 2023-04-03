package com.teamsapp.springboot.TeamsApp.service;

import com.teamsapp.springboot.TeamsApp.entity.Round;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoundService {



    public void saveRound(Round round);

    public List<Round> getRounds();

    public Round getRound(int theId);

    public void deleteRound(int theId);

    public Round findByRoundNumber(int roundNumber);

}
