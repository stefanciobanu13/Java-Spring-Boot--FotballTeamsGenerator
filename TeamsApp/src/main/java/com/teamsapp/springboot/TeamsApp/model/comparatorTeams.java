package com.teamsapp.springboot.TeamsApp.model;
import com.teamsapp.springboot.TeamsApp.entity.Team;
public class comparatorTeams implements java.util.Comparator<Team> {


    public comparatorTeams(){
    }

    @Override
    public int compare(Team o1, Team o2) {


        return o1.getPoints() - o2.getPoints();
    }

}