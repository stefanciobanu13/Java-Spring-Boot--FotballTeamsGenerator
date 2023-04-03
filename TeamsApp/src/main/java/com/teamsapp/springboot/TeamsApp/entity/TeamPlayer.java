package com.teamsapp.springboot.TeamsApp.entity;


import org.springframework.stereotype.Component;

import javax.persistence.*;


@Component
@Entity
@Table(name = "team_player")
public class TeamPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

//    @ManyToOne
//    @JoinColumn(name = "round_id", insertable = false, updatable = false)
//    private Round round;




    // getters, setters and constructors

    public TeamPlayer(Team team, Player player){
            this.team = team;
            this.player= player;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

//    public Round getRound() {
//        return round;
//    }
//
//    public void setRound(Round round) {
//        this.round = round;
//    }

    public TeamPlayer(){}

    public TeamPlayer(int id, Team team, Player player) {
        this.id = id;
        this.team = team;
        this.player = player;
       // this.round = round;
    }
}