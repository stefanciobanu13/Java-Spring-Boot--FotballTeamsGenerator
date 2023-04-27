package com.teamsapp.springboot.TeamsApp.model;

import com.teamsapp.springboot.TeamsApp.entity.Player;

import javax.validation.constraints.Size;
import java.util.ArrayList;

public class Attendance {

    @Size(min = 24, max = 24, message = "The list should contain exactly 24 players")
    private static ArrayList<Player> players;


    private Attendance(){}


    private static class helper{

        private static Attendance attendance = new Attendance();

    }


    public static Attendance getAttendance(){

        return  helper.attendance;
    }

    public static void setList(ArrayList<Player> thePlayers){

        players = new ArrayList<Player>();
        players = thePlayers;
    }

    public void resetAttendance(){
        players = new ArrayList<>();
    }

    public  ArrayList<Player> getAttendanceList(){

        return players;
    }



}
