package com.teamsapp.springboot.TeamsApp.entity;


import com.teamsapp.springboot.TeamsApp.model.PlayersList;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Component
@Table(name = "team")
public class Team {


    //create a PlayersList object
    @Transient
    private PlayersList listObject = new PlayersList();
    @Transient
    private List<Player> thePlayers = getListObject().getList();
    private static int numberLimit = 24;
    private static ArrayList<Player> playersList = new ArrayList<Player>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "color")
    private String color;

    @Column(name = "points")
    private int points;

    @Column(name = "team_value")
    private double teamValue;

    @ManyToMany()
    @JoinTable(
            name = "team_player",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id"))
    private List<Team> players;


    @ManyToOne
    @JoinColumn(name = "round_id")
    private Round round;

    @Transient
    private int scoredGoals = 0;

    @Transient
    private int goalsReceived = 0;

    @Transient
    private int goalsSituation;




//call this method if the team wins
    public void addVictoryPoints(){
        this.points = points + 3;
    }

    //call this method if the team draws
    public void addDrawPoints(){
        this.points ++;
    }


    //constructors
    public Team() {
    }

    public Team(PlayersList listObject, List<Player> thePlayers, int id, String color, double teamValue, List<Team> players, Round round) {
        this.listObject = listObject;
        this.thePlayers = thePlayers;
        this.id = id;
        this.color = color;
        this.teamValue = teamValue;
        this.players = players;
        this.round = round;
    }

    public Team(String color){
        this.color=color;
    }



    //getters and setters


    public int getGoalsSituation() {

        goalsSituation = scoredGoals - goalsReceived;
        return goalsSituation;
    }

    public void setGoalsSituation(int goalsSituation) {
        this.goalsSituation = goalsSituation;
    }

    public int getGoalsReceived() {
        return goalsReceived;
    }

    public void setGoalsReceived(int goalsReceived) {
        this.goalsReceived = goalsReceived;
    }

    public int getScoredGoals() {
        return scoredGoals;
    }

    public void setScoredGoals(int scoredGoals) {
        this.scoredGoals = scoredGoals;
    }

    public static int getNumberLimit() {
        return numberLimit;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public static void setNumberLimit(int numberLimit) {
        Team.numberLimit = numberLimit;
    }

    public static ArrayList<Player> getPlayersList() {
        return playersList;
    }

    public static void setPlayersList(ArrayList<Player> playersList) {
        Team.playersList = playersList;
    }

    public void setThePlayers(List<Player> thePlayers) {
        this.thePlayers = thePlayers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlayers(List<Team> players) {
        this.players = players;
    }

    //getters and setters
    public List<Player> getThePlayers(){

        return thePlayers;
    }

    public PlayersList getListObject() {
        return listObject;
    }

    public List<Player> getPlayers() {

        return thePlayers;
    }

    public double getTeamValue() {
        return teamValue;
    }

    public void setTeamValue(double teamValue) {
        this.teamValue = teamValue;
    }

    public void setListObject(PlayersList listObject) {
        this.listObject = listObject;
    }

    //Getter and setters for colour
    public void setColor(String colour){

        this.color =colour;
    }

    public String getColor(){

        return this.color;
    }



    // a method for adding a player from selecting the player to adding it and remove it from the list
    //the method gets the list of players, and after adding the player, the method updates the list and pass it back
    public void addPlayer(ArrayList<Player> theList) {

        if(getPlayersList().size()==0) {
            matchLists(theList);
        }

        if(this.getNumberLimit() ==0) {
            this.setNumberLimit(24);
        }

        //generate a random number
        Random n1 = new Random();
        int r1 = n1.nextInt(getNumberLimit());

        //get the random player
        Player player = getPlayersList().get(r1);
        this.setNumberLimit(getNumberLimit() - 1);


        //add the player only if the team is not complete and the value remains under 47
        if(teamValue < 47 && thePlayers.size()<6 ) {

            teamValue = teamValue + player.getGrade();

            this.thePlayers.add(player);

        }
        this.getPlayersList().remove(player);

    }

    public void matchLists(ArrayList<Player> list) {

        this.setPlayersList((ArrayList<Player>)list.clone());
    }




    @Override
    public String toString() {
        return "Team [thePlayers=" + thePlayers + ", teamValue=" + teamValue + "]";
    }





}

