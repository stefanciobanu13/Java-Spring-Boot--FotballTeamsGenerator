package com.teamsapp.springboot.TeamsApp.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
public class RoundTeamId implements Serializable {

    //this is a class was made to represent the composite key from the RoundTeam class (which refers to a junction table from the db)


    @Column(name = "team_id")
    private int teamId;

    @Column(name = "round_id")
    private int roundId;

    @Column(name = "team_number")
    private int teamNumber;


    // constructors, getters, and setters

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }

    public RoundTeamId(){}

    public RoundTeamId(int teamId, int roundId, int teamNumber) {
        this.teamId = teamId;
        this.roundId = roundId;
        this.teamNumber = teamNumber;
    }
}