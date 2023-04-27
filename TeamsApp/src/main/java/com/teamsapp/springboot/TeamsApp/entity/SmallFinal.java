package com.teamsapp.springboot.TeamsApp.entity;


import org.springframework.stereotype.Component;

import javax.persistence.*;


@Table(name = "small_final")
@Entity
@Component
public class SmallFinal {

    @Id
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "round_id")
    private Round round;

    @OneToOne
    @JoinColumn(name = "game_id")
    private Game game;

//constructors
    public void SmallFinal(){}

    public SmallFinal(Game game, Round round) {
        this.round = round;
        this.game = game;
    }

    //getters and setters
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
