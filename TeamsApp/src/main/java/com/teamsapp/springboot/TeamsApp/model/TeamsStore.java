package com.teamsapp.springboot.TeamsApp.model;

import com.teamsapp.springboot.TeamsApp.entity.Team;
import org.springframework.stereotype.Component;

@Component
public class TeamsStore {

    public static Team team1;
    public static Team team2;
    public static Team team3;
    public static Team team4;

    private TeamsStore(){}


    private static class helperClass{
        private static TeamsStore teamsStore= new TeamsStore();
    }

public static TeamsStore getInstance(){
        return helperClass.teamsStore;
}



    public  void storeTeam1(Team team1){
        this.team1 = team1;
    }

    public  void storeTeam2(Team team2){
        this.team2 = team2;
    }

    public  void storeTeam3(Team team3){
        this.team3 = team3;
    }

    public  void storeTeam4(Team team4){
        this.team4 = team4;
    }


    public  Team getTeam1() {
        return team1;
    }

    public  Team getTeam2() {
        return team2;
    }

    public  Team getTeam3() {
        return team3;
    }

    public  Team getTeam4() {
        return team4;
    }

    public  void resetTeams(){

        team1 = new Team();
        team2 = new Team();
        team3 = new Team();
        team4 = new Team();
    }











}
