package com.teamsapp.springboot.TeamsApp.service;


import com.teamsapp.springboot.TeamsApp.entity.Player;
import org.springframework.stereotype.Component;
import java.util.List;


@Component
public interface PlayerService {

public void savePlayer(Player player);
	
	public List<Player> getPlayers();
	
	public Player getPlayer(int theId);
	
	public void deletePlayer(int theId);


	
	
}
