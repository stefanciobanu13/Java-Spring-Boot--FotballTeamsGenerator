package com.teamsapp.springboot.TeamsApp.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Entity
@Table(name = "round_team")
public class RoundTeam {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "round_id")
    private Round round;

    @OneToOne
    @JoinColumn(name = "team_id")
    private Team team;



    //constructors, getters and setters

    public RoundTeam(){}

    public RoundTeam(int id, Round round, Team team) {
        this.id = id;
        this.round = round;
        this.team = team;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
