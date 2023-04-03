package com.teamsapp.springboot.TeamsApp.service;


import com.teamsapp.springboot.TeamsApp.entity.TeamPlayer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TeamPlayerService    {

    public void saveTeamPlayer(TeamPlayer teamPlayer);

    public List<TeamPlayer> getTeamPlayers();

    public TeamPlayer getTeamPlayer(int theId);

    public void deleteTeamPlayer(int theId);













}
