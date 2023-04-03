package com.teamsapp.springboot.TeamsApp.entity;


import org.springframework.stereotype.Component;
import javax.persistence.*;


@Entity
@Component
@Table(name = "goal")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "game_id")
    private Game game;


    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;


    @ManyToOne()
    @JoinColumn(name = "player_id")
    private Player player;


        //constructors

    public Goal(){}

    public Goal(int id, Game game, Team team, Player player) {
        this.id = id;
        this.game = game;
        this.team = team;
        this.player = player;
    }

    public Goal(Game game, Team team, Player player) {
        this.game = game;
        this.team = team;
        this.player = player;
    }



    //getters and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
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


}
