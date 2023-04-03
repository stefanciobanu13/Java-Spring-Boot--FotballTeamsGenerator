package com.teamsapp.springboot.TeamsApp.model;

import com.teamsapp.springboot.TeamsApp.entity.Player;
import com.teamsapp.springboot.TeamsApp.entity.Team;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Configurator {

	private Team green = new Team("Green");
	private Team orange = new Team("Orange");
	private Team blue = new Team("Blue");
	private Team gray = new Team("Gray");


	public Configurator() {}


	private ArrayList<Player> playersList;
	private double standardDeviation;
	private double[] teamsValues = new double[4];

	public void configureTeams(ArrayList<Player> List) {
				 	
		ArrayList<Player> theList =(ArrayList<Player>)List.clone();	
		
		//doing the first version of teams
		while(gray.getThePlayers().size()<6) {
			 green.addPlayer(theList);
		     orange.addPlayer(theList);
			 blue.addPlayer(theList);
			 gray.addPlayer(theList);
			
		}	
		
		//storing the values of the teams
		getTeamsValues()[0]=green.getTeamValue();
		getTeamsValues()[1]= orange.getTeamValue();
		getTeamsValues()[2]= blue.getTeamValue();
		getTeamsValues()[3]= gray.getTeamValue();
		
		//calculate the standard deviation of the teams values
		 double stdDev = StandardDeviation.calculateSD(getTeamsValues());
		 this.setStandardDeviation(stdDev);
		 
		 //call the method that will improve the teams
		 generateTeams(List);
	}
	
		
	public void generateTeams(ArrayList<Player> List) {
		
		ArrayList<Player> theList = List;
		boolean teamsComplete = false;	
		
		//allocate players to the teams as long as the teams are not complete and standard deviation is not reached
			while(this.getStandardDeviation() >0.3 || teamsComplete == false) {
				
				//empty the teams if the standard deviation is not reached
				if(gray.getThePlayers().size()==6) {
					resetTeams();
					teamsComplete = false;
				}	
				
			 green.addPlayer(List);
			 orange.addPlayer(List);
			 blue.addPlayer(List);
			 gray.addPlayer(List);
			 
			//update the values of the teams
			getTeamsValues()[0]=green.getTeamValue();
			getTeamsValues()[1]= orange.getTeamValue();
			getTeamsValues()[2]= blue.getTeamValue();
			getTeamsValues()[3]= gray.getTeamValue();
			
			//recalculate the standard deviation
			 double stdDev = StandardDeviation.calculateSD(getTeamsValues());
			 this.setStandardDeviation(stdDev);
			 if(gray.getThePlayers().size()==6) {
				 teamsComplete = true;
			 }
			
		}	
	}
	
	public void resetTeams() {
		//empty the teams
		this.green = new Team("Green");
		this.orange = new Team("Orange");
		this.blue = new Team("Blue");
		this.gray = new Team("Gray");
	}
		
//getters and setters
	public Team getGreen() {
		return green;
	}

	public Team getOrange() {
		return orange;
	}

	public Team getBlue() {
		return blue;
	}

	public Team getGray() {
		return gray;
	}

	
	public void setTeam1(Team green) {
		this.green = green;
	}

	public void setOrange(Team orange) {
		this.orange = orange;
	}

	public void setBlue(Team blue) {
		this.blue = blue;
	}

	public void setGray(Team gray) {
		this.gray = gray;
	}



	public double getStandardDeviation() {
		return standardDeviation;
	}


	public ArrayList<Player> getPlayersList() {
		return playersList;
	}

	public void setPlayersList(ArrayList<Player> playersList) {
		this.playersList = playersList;
	}

	public void setStandardDeviation(double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

	public double[] getTeamsValues() {
		return teamsValues;
	}

	public void setTeamsValues(double[] teamsValues) {
		this.teamsValues = teamsValues;
	}
}
