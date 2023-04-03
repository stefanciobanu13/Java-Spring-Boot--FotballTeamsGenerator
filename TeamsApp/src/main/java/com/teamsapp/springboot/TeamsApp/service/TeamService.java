package com.teamsapp.springboot.TeamsApp.service;





import com.teamsapp.springboot.TeamsApp.entity.Team;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TeamService {


	public void saveTeam(Team team);
	
	public List<Team> getTeams();
	
	public Team getTeam(int theId);
	
	public void deleteTeam(int theId);

	public void updatePointsById(int teamId, int points);


}
