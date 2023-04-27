package com.teamsapp.springboot.TeamsApp.entity;

import org.springframework.stereotype.Component;
import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Component
@Table(name = "round")
public class Round {

    @OneToOne
    @JoinColumn(name = "team1_id")
    private Team team1_id;
    @OneToOne
    @JoinColumn(name = "team2_id")
    private Team team2_id;
    @OneToOne
    @JoinColumn(name = "team3_id")
    private Team team3_id;
    @OneToOne
    @JoinColumn(name = "team4_id")
    private Team team4_id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Pattern(regexp = "^\\d{2}\\.\\d{2}\\.\\d{4}$", message = "Date must be in the format day.month.year")
    @Column(name = "date")
    private String date;

    @Column(name = "number")
    private int number;


    //constructors


    public Round(Team team1_id, Team team2_id, Team team3_id, Team team4_id, int id, String date, int number) {
        this.team1_id = team1_id;
        this.team2_id = team2_id;
        this.team3_id = team3_id;
        this.team4_id = team4_id;
        this.id = id;
        this.date = date;
        this.number = number;
    }

    public Round(){}


    //getters and setters

    public Team getTeam1_id() {
        return team1_id;
    }

    public void setTeam1_id(Team team1_id) {
        this.team1_id = team1_id;
    }

    public Team getTeam2_id() {
        return team2_id;
    }

    public void setTeam2_id(Team team2_id) {
        this.team2_id = team2_id;
    }

    public Team getTeam3_id() {
        return team3_id;
    }

    public void setTeam3_id(Team team3_id) {
        this.team3_id = team3_id;
    }

    public Team getTeam4_id() {
        return team4_id;
    }

    public void setTeam4_id(Team team4_id) {
        this.team4_id = team4_id;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
