package com.teamsapp.springboot.TeamsApp.model;

import org.springframework.stereotype.Component;

@Component
public class GameCounter {

    private static int gameCounter = 0;

    public GameCounter(){
    }

    public int getGameCounter() {
        return gameCounter;
    }

    public void setGameCounter(int gameCounter) {
        this.gameCounter = gameCounter;
    }

    public  static int getTheCounter(){
        return gameCounter;
    }

    public static void resetTheCounter(){
        gameCounter=0;
    }
    public static void increaseCounter(){
        gameCounter= gameCounter +1;
    }
}
