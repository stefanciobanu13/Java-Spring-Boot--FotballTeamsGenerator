package com.teamsapp.springboot.TeamsApp.service;

import java.util.List;
import java.util.Optional;

import com.teamsapp.springboot.TeamsApp.dao.PlayerDAO;
import com.teamsapp.springboot.TeamsApp.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class PlayerServiceImp implements PlayerService {

	private PlayerDAO playerDAO;

	@Autowired
	public PlayerServiceImp(PlayerDAO playerDAO){

		this.playerDAO = playerDAO;

	}

	@Transactional
	@Override
	public void savePlayer(Player player) {

		playerDAO.save(player);
	}

	@Transactional
	@Override
	public List<Player> getPlayers() {

		return playerDAO.findAll();
	}

	@Transactional
	@Override
	public Player getPlayer(int theId) {

		Optional<Player> optionalPlayer = playerDAO.findById(theId);
		Player player = optionalPlayer.get();

		return player;
	}

	@Transactional
	@Override
	public void deletePlayer(int theId) {
		playerDAO.deleteById(theId);

	}




}
