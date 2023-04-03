package com.teamsapp.springboot.TeamsApp.service;

import com.teamsapp.springboot.TeamsApp.dao.TeamPlayerDAO;
import com.teamsapp.springboot.TeamsApp.entity.TeamPlayer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamPlayerServiceImp  implements TeamPlayerService{


    private TeamPlayerDAO teamPlayerDAO;

    public TeamPlayerServiceImp(TeamPlayerDAO teamPlayerDAO){

        this.teamPlayerDAO = teamPlayerDAO;
    }


    @Override
    public void saveTeamPlayer(TeamPlayer teamPlayer) {

        teamPlayerDAO.save(teamPlayer);
    }

    @Override
    public List<TeamPlayer> getTeamPlayers() {

        return teamPlayerDAO.findAll();
    }

    @Override
    public TeamPlayer getTeamPlayer(int theId) {

        Optional<TeamPlayer> teamPlayerOptional = teamPlayerDAO.findById(theId);
        TeamPlayer tempTeamPlayer = teamPlayerOptional.get();

        return tempTeamPlayer;
    }

    @Override
    public void deleteTeamPlayer(int theId) {

        teamPlayerDAO.deleteById(theId);
    }
}
