package com.teamsapp.springboot.TeamsApp.service;

import com.teamsapp.springboot.TeamsApp.dao.TeamDAO;
import com.teamsapp.springboot.TeamsApp.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class TeamServiceImp implements TeamService {


	private TeamDAO teamDAO;

	@Autowired
	public TeamServiceImp(TeamDAO teamDAO){

		this.teamDAO = teamDAO;

	}


	@Transactional
	@Override
	public List<Team> getTeams() {


		return teamDAO.findAll();
	}

	@Transactional
	@Override
	public void deleteTeam(int theId) {

		teamDAO.deleteById(theId);
	}

	@Override
	public void updatePointsById(int teamId, int points) {
		teamDAO.updatePointsById(teamId,points);
	}

	@Transactional
	@Override
	public void saveTeam(Team team) {

		teamDAO.save(team);

	}


	@Transactional
	@Override
	public Team getTeam(int teamId) {

		Optional<Team> optionalTeam = teamDAO.findById(teamId);
		Team tempTeam = optionalTeam.get();

		return tempTeam;
	}
}
