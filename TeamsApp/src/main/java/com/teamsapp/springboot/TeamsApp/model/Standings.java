package com.teamsapp.springboot.TeamsApp.model;


import com.teamsapp.springboot.TeamsApp.entity.Team;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class Standings {

    private static Team p1;
    private static Team p2;
    private static Team p3;
    private static Team p4;


    //contructors
    public Standings(){}

    public Standings(Team green, Team orange, Team blue, Team gray){
        this.p1 = green;
        this.p2 = orange;
        this.p3 = blue;
        this.p4 = gray;
    }


    public List<Team> refreshStandings(){

        TeamsStore teamsStore = TeamsStore.getInstance();

        //get the teams from the store
        Team green = teamsStore.getTeam1();
        Team orange = teamsStore.getTeam2();
        Team blue = teamsStore.getTeam3();
        Team gray = teamsStore.getTeam4();

        ArrayList<Team> theTeams = new ArrayList<>();
        theTeams.add(green);
        theTeams.add(orange);
        theTeams.add(blue);
        theTeams.add(gray);
        //compare the teams through the number of points
        Collections.sort(theTeams, new comparatorTeams());
        this.p1 = theTeams.get(3);
        this.p2 = theTeams.get(2);
        this.p3= theTeams.get(1);
        this.p4= theTeams.get(0);

        return theTeams;
    }

    //get all positions
    public List<Team> getStandings(){
        List<Team> standings = new ArrayList<>();
        standings.add(p1);
        standings.add(p2);
        standings.add(p3);
        standings.add(p4);
        return standings;
    }


    //getters and setters


    public Team getP1() {
        return p1;
    }

    public void setP1(Team p1) {
        this.p1 = p1;
    }

    public Team getP2() {
        return p2;
    }

    public void setP2(Team p2) {
        this.p2 = p2;
    }

    public Team getP3() {
        return p3;
    }

    public void setP3(Team p3) {
        this.p3 = p3;
    }

    public Team getP4() {
        return p4;
    }

    public void setP4(Team p4) {
        this.p4 = p4;
    }


}
