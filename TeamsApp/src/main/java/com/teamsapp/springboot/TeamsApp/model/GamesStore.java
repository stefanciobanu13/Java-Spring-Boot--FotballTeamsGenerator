package com.teamsapp.springboot.TeamsApp.model;

import com.teamsapp.springboot.TeamsApp.entity.Game;
import com.teamsapp.springboot.TeamsApp.entity.Round;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class GamesStore {


    private TeamsStore teamsStore = TeamsStore.getInstance();
    private GamesStore(){
    }

    private static class helperClass{
         private static GamesStore gamesStore = new GamesStore();
    }


    public static GamesStore getInstance(){

        return helperClass.gamesStore;
    }


    private Game game1;
    private Game game2;
    private Game game3;
    private Game game4;
    private Game game5;
    private Game game6;
    private Game game7;
    private Game game8;
    private Game game9;
    private Game game10;
    private Game game11;
    private Game game12;
    private Game smallFinal;
    private Game bigFinal;


    private List<Game> gamesList;


    public void configureTheGames(Round round){

        game1 = new Game(teamsStore.getTeam1(),teamsStore.getTeam2(),round);
        game2 = new Game(teamsStore.getTeam3(),teamsStore.getTeam4(),round);
        game3 = new Game(teamsStore.getTeam2(),teamsStore.getTeam3(),round);
        game4 = new Game(teamsStore.getTeam4(),teamsStore.getTeam1(),round);
        game5 = new Game(teamsStore.getTeam1(),teamsStore.getTeam3(),round);
        game6 = new Game(teamsStore.getTeam2(),teamsStore.getTeam4(),round);

        game7 = new Game(teamsStore.getTeam3(),teamsStore.getTeam1(),round);
        game8 = new Game(teamsStore.getTeam4(),teamsStore.getTeam2(),round);
        game9 = new Game(teamsStore.getTeam1(),teamsStore.getTeam4(),round);
        game10 = new Game(teamsStore.getTeam3(),teamsStore.getTeam2(),round);
        game11 = new Game(teamsStore.getTeam2(),teamsStore.getTeam1(),round);
        game12 = new Game(teamsStore.getTeam4(),teamsStore.getTeam3(),round);

    }

//store the games into a list
public List<Game> getTheGames(){

        if(gamesList == null){
            gamesList = new ArrayList<>();
            gamesList.add(game1);
            gamesList.add(game2);
            gamesList.add(game3);
            gamesList.add(game4);
            gamesList.add(game5);
            gamesList.add(game6);
            gamesList.add(game7);
            gamesList.add(game8);
            gamesList.add(game9);
            gamesList.add(game10);
            gamesList.add(game11);
            gamesList.add(game12);
        }
        return gamesList;
}


//a method which saves the curernt game back in the store
    public void storeCurrentGame(Game theGame, int index){
            gamesList.set(index,theGame);

    }

//resets the teams from the games
    public void resetGames(){
         Game game1 = new Game();
         Game game2 = new Game();
         Game game3 = new Game();
         Game game4 = new Game();
         Game game5 = new Game();
         Game game6 = new Game();
         Game game7 = new Game();
         Game game8 = new Game();
         Game game9 = new Game();
         Game game10 = new Game();
         Game game11 = new Game();
         Game game12 = new Game();
    }

//getters and setters

    public Game getSmallFinal() {
        return smallFinal;
    }

    public void setSmallFinal(Game smallFinal) {
        this.smallFinal = smallFinal;
    }

    public Game getBigFinal() {
        return bigFinal;
    }

    public void setBigFinal(Game bigFinal) {
        this.bigFinal = bigFinal;
    }

    public Game getGame1() {
        return game1;
    }

    public void setGame1(Game game1) {
        this.game1 = game1;
    }

    public Game getGame2() {
        return game2;
    }

    public void setGame2(Game game2) {
        this.game2 = game2;
    }

    public Game getGame3() {
        return game3;
    }

    public void setGame3(Game game3) {
        this.game3 = game3;
    }

    public Game getGame4() {
        return game4;
    }

    public void setGame4(Game game4) {
        this.game4 = game4;
    }

    public Game getGame5() {
        return game5;
    }

    public void setGame5(Game game5) {
        this.game5 = game5;
    }

    public Game getGame6() {
        return game6;
    }

    public void setGame6(Game game6) {
        this.game6 = game6;
    }

    public Game getGame7() {
        return game7;
    }

    public void setGame7(Game game7) {
        this.game7 = game7;
    }

    public Game getGame8() {
        return game8;
    }

    public void setGame8(Game game8) {
        this.game8 = game8;
    }

    public Game getGame9() {
        return game9;
    }

    public void setGame9(Game game9) {
        this.game9 = game9;
    }

    public Game getGame10() {
        return game10;
    }

    public void setGame10(Game game10) {
        this.game10 = game10;
    }

    public Game getGame11() {
        return game11;
    }

    public void setGame11(Game game11) {
        this.game11 = game11;
    }

    public Game getGame12() {
        return game12;
    }

    public void setGame12(Game game12) {
        this.game12 = game12;
    }





}
