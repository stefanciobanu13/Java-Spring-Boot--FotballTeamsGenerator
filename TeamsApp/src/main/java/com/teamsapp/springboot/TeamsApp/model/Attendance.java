package com.teamsapp.springboot.TeamsApp.model;

import com.teamsapp.springboot.TeamsApp.entity.Player;

import java.util.ArrayList;

public class Attendance {


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
