package com.teamsapp.springboot.TeamsApp.entity;

import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.util.ArrayList;

@Table(name ="game")
@Entity
@Component
public class Game {


    @Id
    @Column(name = "id")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "team1_id")
    private Team team1;

   @Column(name = "winner")
    private int winner;

    @Transient
    private ArrayList<Player> goalScorersT1 = new ArrayList<>();
    @Transient
    private ArrayList<Player> goalScorersT2 = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "team2_id")
    private Team team2;

    @OneToOne()
    @JoinColumn(name = "round_id")
    private Round round;

    @Column(name = "score_team1")
    private int score_team1 = 0;

    @Column(name = "score_team2")
    private int score_team2 = 0;

    //constructors
    public Game(){}

    public Game(int id, Team team1, ArrayList<Player> goalScorersT1, ArrayList<Player> goalScorersT2, Team team2, int score_team1, int score_team2) {
        this.id = id;
        this.team1 = team1;
        this.goalScorersT1 = goalScorersT1;
        this.goalScorersT2 = goalScorersT2;
        this.team2 = team2;
        this.score_team1 = score_team1;
        this.score_team2 = score_team2;
    }

    public Game(Team team1, Team team2, Round round){

        this.team1 = team1;
        this.team2 = team2;
        this.round = round;

    }

    //a method which sets the number of goals for each team
    public void setTheScore(){
        for(Player p : goalScorersT1){
            this.score_team1++;
        }

        for(Player p : goalScorersT2){
            this.score_team2++;
        }

    }
    public void goalTeam1(Player player){

        score_team1++;
        goalScorersT1.add(player);
    }

    public void goalTeam2(Player player){

        score_team2++;
        goalScorersT2.add(player);
    }

    //getters and setters


    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public int getScore_team1() {
        return score_team1;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public int getScore_team2() {
        return score_team2;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Player> getGoalScorersT1() {
        return goalScorersT1;
    }

    public void setGoalScorersT1(ArrayList<Player> goalScorersT1) {
        this.goalScorersT1 = goalScorersT1;
    }

    public ArrayList<Player> getGoalScorersT2() {
        return goalScorersT2;
    }

    public void setGoalScorersT2(ArrayList<Player> goalScorersT2) {
        this.goalScorersT2 = goalScorersT2;
    }

    public void setScore_team1(int team1Goals) {
        this.score_team1 = team1Goals;
    }

    public void setScore_team2(int team2Goals) {
        this.score_team2 = team2Goals;
    }
}
