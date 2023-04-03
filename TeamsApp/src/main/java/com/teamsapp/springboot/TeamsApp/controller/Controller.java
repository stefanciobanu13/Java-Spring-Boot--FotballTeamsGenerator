package com.teamsapp.springboot.TeamsApp.controller;

import com.teamsapp.springboot.TeamsApp.entity.*;
import com.teamsapp.springboot.TeamsApp.model.*;
import com.teamsapp.springboot.TeamsApp.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private TeamPlayerService teamPlayerService;
    @Autowired
    private RoundService roundService;
    @Autowired
    private Configurator configurator;

    @Autowired
    private GoalService goalService;

    @Autowired
    private GameService gameService;

    @Autowired
    private Standings standings;

    @Autowired
    private GameCounter gameCounter;



    @GetMapping("/home")
    private String home(){

        return "home";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {

        //create model attribute to bind from data

        Player thePlayer = new Player();
        theModel.addAttribute("player",thePlayer);

        return "playerForm";
    }

    @PostMapping("/savePlayer")
    public String savePlayer(@ModelAttribute("player")Player thePlayer) {

        //save the customer using service
        playerService.savePlayer(thePlayer);

        return "redirect:/showFormForAdd";
    }

    @PostMapping("/updatePlayer")
    public String updatePlayer(@ModelAttribute("player")Player thePlayer) {

        //save the customer using service
        playerService.savePlayer(thePlayer);

        return "redirect:/showDbPlayers";
    }

    @RequestMapping("/deletePlayer")
    public String deletePlayer(@RequestParam("playerId") int theId){

        playerService.deletePlayer(theId);


        return "redirect:/showDbPlayers";
    }

    @RequestMapping("showFormForUpdate")
    public String showFormForUpdate(@RequestParam("playerId") int theId, Model model) {

        //get the player
        Player tempPlayer = playerService.getPlayer(theId);

        //put the player into the model so the form can pre populate the fields
        model.addAttribute("player",tempPlayer);

        return "updateForm";
    }

    @GetMapping("/showPlayers")
    public String showPlayers(Model model) {

        //get players from DAO

        List<Player> thePlayers = playerService.getPlayers();

        //store the players into the model
        model.addAttribute("thePlayers",thePlayers);
        return "show-player";
    }

    @GetMapping("/showDbPlayers")
    public String showDbPlayers(Model model) {

        //get players from DAO

        List<Player> thePlayers = playerService.getPlayers();

        //store the players into the model
        model.addAttribute("thePlayers",thePlayers);
        return "db-players";
    }

    @PostMapping("/submitAttendance")
    public String submitAttendance(@RequestParam("selectedPlayers") ArrayList<Integer> selectedPlayers,
                                   Model model){

        ArrayList<Integer> ids = selectedPlayers;
        ArrayList<Player> players = new ArrayList<Player>();

        for(Integer i :ids){
            Player tempPlayer = playerService.getPlayer(i);
            players.add(tempPlayer);

        }

        model.addAttribute("players",players);

        //get an Attendance variable
        Attendance attendance = Attendance.getAttendance();
        //reset the Attendance
        attendance.resetAttendance();

        //store the players list into the Attendance static variable
        Attendance.setList(players);

        return "show-attendance";
    }
    @GetMapping("/generateTeams")
    public String generateTeams( Model model) {

        //get an instance of the Attendance variable
        Attendance attendance = Attendance.getAttendance();

        //get the list from the attendance
        ArrayList<Player> thePlayers = attendance.getAttendanceList();

        System.out.println("generating teams" + "first is" + thePlayers.get(0));

        //pass the list of players to the configurator
        configurator.configureTeams(thePlayers);

        //store the teams into the teams store
        TeamsStore  teamsStore = TeamsStore.getInstance();

        teamsStore.resetTeams();

        teamsStore.storeTeam1(configurator.getGreen());
        teamsStore.storeTeam2(configurator.getOrange());
        teamsStore.storeTeam3(configurator.getBlue());
        teamsStore.storeTeam4(configurator.getGray());

        //get the teams
        Team teamGreen = teamsStore.getTeam1();
        Team teamOrange= teamsStore.getTeam2();
        Team teamBlue = teamsStore.getTeam3();
        Team teamGray = teamsStore.getTeam4();

        model.addAttribute("teamGreen",teamGreen);
        model.addAttribute("teamOrange",teamOrange);
        model.addAttribute("teamBlue",teamBlue);
        model.addAttribute("teamGray",teamGray);

        return "generate-teams";
    }

    @GetMapping("/startGame")
    public String startGame(@RequestParam("date") String date,
                            @RequestParam("number") int number,
                            Model model){

        //getting and storing the round details

        int number1 = number;
        String date1 = date;
        Round round = new Round();
        round.setDate(date1);
        round.setNumber(number1);
        //saving the round in database
        roundService.saveRound(round);
        //get the round back including the id
        Round theRound = roundService.findByRoundNumber(number);
        System.out.println("the id of the ROUND IS" + " " + theRound.getId());

        //getting the teams from the store and save them into the database
        TeamsStore teamsStore = TeamsStore.getInstance();
        Team teamGreen = teamsStore.getTeam1();
        Team teamOrange = teamsStore.getTeam2();
        Team teamBlue = teamsStore.getTeam3();
        Team teamGray = teamsStore.getTeam4();

        //save the round into the teams
        teamGreen.setRound(theRound);
        teamOrange.setRound(theRound);
        teamBlue.setRound(theRound);
        teamGray.setRound(theRound);

        //configure the games
        GamesStore gamesStore = GamesStore.getInstance();
        gamesStore.configureTheGames(theRound);

        //get the first game
        Game game1 = gamesStore.getGame1();

        //save the teams  into db
        teamService.saveTeam(teamGreen);
        teamService.saveTeam(teamOrange);
        teamService.saveTeam(teamBlue);
        teamService.saveTeam(teamGray);

        //persisting every player and his team, on the junction table team_player
        //saving the players of team green
        for( Player p : teamGreen.getThePlayers()){
            TeamPlayer teamPlayer = new TeamPlayer(teamGreen,p);
            teamPlayerService.saveTeamPlayer(teamPlayer);
        }
        //saving the players of team orange
        for( Player p : teamOrange.getThePlayers()){
            TeamPlayer teamPlayer = new TeamPlayer(teamOrange,p);
            teamPlayerService.saveTeamPlayer(teamPlayer);
        }
        //saving the players of team blue
        for( Player p : teamBlue.getThePlayers()){
            TeamPlayer teamPlayer = new TeamPlayer(teamBlue,p);
            teamPlayerService.saveTeamPlayer(teamPlayer);
        }
        //saving the players of team orange
        for( Player p : teamGray.getThePlayers()){
            TeamPlayer teamPlayer = new TeamPlayer(teamGray,p);
            teamPlayerService.saveTeamPlayer(teamPlayer);
        }

        //create and pass an empty player model
        Player player = new Player();

        Goal goal =  new Goal();

        //set the standings
        Standings standings = new Standings(teamGreen,teamOrange,teamBlue,teamGray);

        //retrieve the teams and their position
        Team p1 = standings.getP1();
        Team p2 = standings.getP2();
        Team p3 = standings.getP3();
        Team p4 = standings.getP4();
        //pass the to modelss
        model.addAttribute("p1",p1);
        model.addAttribute("p2",p2);
        model.addAttribute("p3",p3);
        model.addAttribute("p4",p4);

        //pass all the data into the models
        model.addAttribute("teamGreen",teamGreen);
        model.addAttribute("teamOrange",teamOrange);
        model.addAttribute("teamBlue",teamBlue);
        model.addAttribute("teamGray",teamGray);
        model.addAttribute("player",player);
        model.addAttribute("currentGame",game1);
        model.addAttribute("round",theRound);
        model.addAttribute("goal",goal);


        return "roundForm";

    }

    @PostMapping("/goalTeam1")
    public String goalTeam1(@RequestParam("scorerId") int scorerId,Model model){

        int theId = scorerId;
        Player scorer = playerService.getPlayer(theId);
        System.out.println("the scorer of team1 is" + " " + scorer);
        //get the current game
        GamesStore gamesStore  = GamesStore.getInstance();
        int gameCounter = GameCounter.getTheCounter();
        //use the gameCounter to get the curent game from the list of games
        Game currentGame = gamesStore.getTheGames().get(gameCounter);
        for (Player p : currentGame.getTeam1().getThePlayers()){
                if(scorer.getId() == p.getId()){
                    currentGame.goalTeam1(scorer);
                }
        }

        //getting the teams from the store
        TeamsStore teamsStore = TeamsStore.getInstance();
        Team Green = teamsStore.getTeam1();
        Team Orange = teamsStore.getTeam2();
        Team Blue = teamsStore.getTeam3();
        Team Gray = teamsStore.getTeam4();

        List<Player> scorersT1  = currentGame.getGoalScorersT1();
        List<Player> scorersT2  = currentGame.getGoalScorersT2();

        //set the scored goals to every team
        setScoredGoals(Green,Orange,Blue,Gray);

        //get the teams from the store again(including the goals scored)
        Team teamGreen = teamsStore.getTeam1();
        Team teamOrange = teamsStore.getTeam2();
        Team teamBlue = teamsStore.getTeam3();
        Team teamGray = teamsStore.getTeam4();

        Standings standings1 = new Standings(teamGreen,teamOrange,teamBlue,teamGray);

        //retrieve the teams and their position
        Team p1 = standings1.getP1();
        Team p2 = standings1.getP2();
        Team p3 = standings1.getP3();
        Team p4 = standings1.getP4();
        //pass the to modelss
        model.addAttribute("p1",p1);
        model.addAttribute("p2",p2);
        model.addAttribute("p3",p3);
        model.addAttribute("p4",p4);

        //storing all the data in models
        model.addAttribute("teamGreen",teamGreen);
        model.addAttribute("teamOrange",teamOrange);
        model.addAttribute("teamBlue",teamBlue);
        model.addAttribute("teamGray",teamGray);
        model.addAttribute("currentGame",currentGame);
        model.addAttribute("scorersT1",scorersT1);



        return "roundForm";
    }

    @PostMapping("/goalTeam2")
    public String goalTeam2(@RequestParam("scorerId") int scorerId, Model model){
        int theId = scorerId;
        Player scorer = playerService.getPlayer(theId);

        //get the current game
        GamesStore gamesStore  = GamesStore.getInstance();
        int gameCounter = GameCounter.getTheCounter();
        //use the gameCounter to get the curent game from the list of games

        Game currentGame = gamesStore.getTheGames().get(gameCounter);
        for (Player p : currentGame.getTeam2().getThePlayers()){
            if(scorer.getId() == p.getId()){
                currentGame.goalTeam2(scorer);
            }
        }

        //store it back in the store
        gamesStore.storeCurrentGame(currentGame,gameCounter);


        //getting the teams from the store
        TeamsStore teamsStore = TeamsStore.getInstance();
        Team Green = teamsStore.getTeam1();
        Team Orange = teamsStore.getTeam2();
        Team Blue = teamsStore.getTeam3();
        Team Gray = teamsStore.getTeam4();

        List<Player> scorersT1  = currentGame.getGoalScorersT1();
        List<Player> scorersT2  = currentGame.getGoalScorersT2();

        //set the scored goals to every team
        setScoredGoals(Green,Orange,Blue,Gray);

        //get the teams from the store again(including the goals scored)
        Team teamGreen = teamsStore.getTeam1();

        Team teamOrange = teamsStore.getTeam2();

        Team teamBlue = teamsStore.getTeam3();

        Team teamGray = teamsStore.getTeam4();

        Standings standings1 = new Standings(teamGreen,teamOrange,teamBlue,teamGray);

        //retrieve the teams and their position
        Team p1 = standings1.getP1();
        Team p2 = standings1.getP2();
        Team p3 = standings1.getP3();
        Team p4 = standings1.getP4();
        //pass the to models
        model.addAttribute("p1",p1);
        model.addAttribute("p2",p2);
        model.addAttribute("p3",p3);
        model.addAttribute("p4",p4);
        System.out.println("the color  of  p1 is" + p1.getColor());


        //storing all the data in models
        model.addAttribute("teamGreen",teamGreen);
        model.addAttribute("teamOrange",teamOrange);
        model.addAttribute("teamBlue",teamBlue);
        model.addAttribute("teamGray",teamGray);
        model.addAttribute("currentGame",currentGame);
        model.addAttribute("scorersT1",scorersT1);
        model.addAttribute("scorersT2",scorersT2);


        return "roundForm";
    }


    @GetMapping("/nextGame")
    public String nextGame(Model model){

        //save the current game into the database
        GamesStore gamesStore = GamesStore.getInstance();
        List<Game> theGames = gamesStore.getTheGames();
        //get the game counter
        int gameCounter = GameCounter.getTheCounter();
        Game currentGame = theGames.get(gameCounter);
        gamesStore.storeCurrentGame(currentGame,gameCounter);
        //persist the game
        gameService.saveGame(currentGame);

        //give the points to the teams
        if(currentGame.getScore_team1() >currentGame.getScore_team2() ){
            setVictoryPoints(currentGame.getTeam1());
        }
        if(currentGame.getScore_team2() >currentGame.getScore_team1()){
            setVictoryPoints(currentGame.getTeam2());
        }
        if(currentGame.getScore_team1() ==currentGame.getScore_team2()){
            setDrawPoints(currentGame.getTeam1(),currentGame.getTeam2());
        }

        //get the game back including the id
        Game theCurrentGame = gameService.findByTeamsIds(currentGame.getTeam1().getId(),currentGame.getTeam2().getId());

        //increase the game counter
        GameCounter.increaseCounter();

        //create the goals and save them in db
        for(Player p : currentGame.getGoalScorersT1()){
            Goal goalT1 = new Goal(theCurrentGame,theCurrentGame.getTeam1(),p);
            goalService.saveGoal(goalT1);
        }

        for(Player p : currentGame.getGoalScorersT2()){
            Goal goalT2 = new Goal(theCurrentGame,theCurrentGame.getTeam2(),p);
            goalService.saveGoal(goalT2);
        }

        //getting the teams from the store
        TeamsStore teamsStore = TeamsStore.getInstance();
        Team Green = teamsStore.getTeam1();
        Team Orange = teamsStore.getTeam2();
        Team Blue = teamsStore.getTeam3();
        Team Gray = teamsStore.getTeam4();

        //set the scored goals to every team
        setScoredGoals(Green,Orange,Blue,Gray);
        setReceivedGoals(Green,Orange,Blue,Gray);
        //substract the scored goals from the recieved ones, on every team
        substractTheGoals(Green,Orange,Blue,Gray);


        //get the teams from the store again(including the goals scored)
        Team teamGreen = teamsStore.getTeam1();
        System.out.println("FROM STORE green scored " + " "+ teamGreen.getScoredGoals());

        Team teamOrange = teamsStore.getTeam2();
        System.out.println("FROM STORE orange scored " + " "+ teamOrange.getScoredGoals());

        Team teamBlue = teamsStore.getTeam3();
        System.out.println("FROM STORE blue scored " + " "+ teamBlue.getScoredGoals());

        Team teamGray = teamsStore.getTeam4();
        System.out.println("FROM STORE gray scored " + " "+ teamGray.getScoredGoals());

        //set the standings
        Standings standings = new Standings(teamGreen,teamOrange,teamBlue,teamGray);
        List<Team> theTeams =  standings.refreshStandings();

        //retrieve the teams and their position
        Team p1 = standings.getP1();
        Team p2 = standings.getP2();
        Team p3 = standings.getP3();
        Team p4 = standings.getP4();

        //pass the to models
        model.addAttribute("p1",p1);
        System.out.println("the color of p1 is " + "" + p1.getColor());
        model.addAttribute("p2",p2);
        System.out.println("the color of p2 is " + "" + p2.getColor());

        model.addAttribute("p3",p3);
        System.out.println("the color of p3 is " + "" + p3.getColor());

        model.addAttribute("p4",p4);
        System.out.println("the color of p4 is " + "" + p4.getColor());

        model.addAttribute("theTeams",theTeams);


        int gameCounter2 = GameCounter.getTheCounter();
        Game nextGame = gamesStore.getTheGames().get(gameCounter2);

        List<Player> scorersT1  = nextGame.getGoalScorersT1();
        List<Player> scorersT2  = nextGame.getGoalScorersT2();

        model.addAttribute("teamGreen",teamGreen);
        model.addAttribute("teamOrange",teamOrange);
        model.addAttribute("teamBlue",teamBlue);
        model.addAttribute("teamGray",teamGray);
        model.addAttribute("currentGame",nextGame);
        model.addAttribute("scorersT1",scorersT1);
        model.addAttribute("scorersT2",scorersT2);

        return "roundForm";
    }


    public Team setVictoryPoints(Team team){

        //add the points
        Team tempTeam = team;
        tempTeam.addVictoryPoints();
        //store the team in the Store
        TeamsStore teamsStore = TeamsStore.getInstance();
        //update the points in database
        teamService.updatePointsById(tempTeam.getId(), tempTeam.getPoints());

        if(tempTeam.getColor() == "Green") {
            teamsStore.storeTeam1(tempTeam);
        }else{
            if(tempTeam.getColor() == "Orange"){
                teamsStore.storeTeam2(tempTeam);
            }else{
                if(tempTeam.getColor() == "Blue"){
                    teamsStore.storeTeam3(tempTeam);
                }else {
                    if(tempTeam.getColor() == "Gray"){
                        teamsStore.storeTeam4(tempTeam);
                    }
                }
            }
        }


        return tempTeam;
    }
    public void setDrawPoints(Team team1, Team team2){

        Team tempTeam1 = team1;
        tempTeam1.addDrawPoints();
        TeamsStore teamsStore = TeamsStore.getInstance();
        //update the points in database
        teamService.updatePointsById(tempTeam1.getId(), tempTeam1.getPoints());

        if(tempTeam1.getColor() == "Green") {
            teamsStore.storeTeam1(tempTeam1);
        }else{

            if(tempTeam1.getColor() == "Orange"){
                teamsStore.storeTeam2(tempTeam1
                );
            }else{
                if(tempTeam1.getColor() == "Blue"){
                    teamsStore.storeTeam3(tempTeam1);
                }else {
                    if(tempTeam1.getColor() == "Gray"){
                        teamsStore.storeTeam4(tempTeam1);
                    }
                }
            }
        }

        Team tempTeam2 = team2;
        tempTeam2.addDrawPoints();
        //update the points in database
        teamService.updatePointsById(tempTeam2.getId(), tempTeam2.getPoints());

        if(tempTeam2.getColor() == "Green") {
            teamsStore.storeTeam1(tempTeam2);
        }else{
            if(tempTeam2.getColor() == "Orange"){
                teamsStore.storeTeam2(tempTeam2);
            }else{
                if(tempTeam2.getColor() == "Blue"){
                    teamsStore.storeTeam3(tempTeam2);
                }else {
                    if(tempTeam2.getColor() == "Gray"){
                        teamsStore.storeTeam4(tempTeam2);
                    }
                }
            }
        }



    }
    public void  setScoredGoals(Team green, Team orange, Team blue, Team gray){

        Team teamGreen = green;
        int greenGoals = goalService.getTeamGoals(teamGreen.getId());
        teamGreen.setScoredGoals(greenGoals);

        Team teamOrange = orange;
        int orangeGoals = goalService.getTeamGoals(orange.getId());
        teamOrange.setScoredGoals(orangeGoals);

        Team teamBlue = blue;
        int blueGoals = goalService.getTeamGoals(teamBlue.getId());
        teamBlue.setScoredGoals(blueGoals);

        Team teamGray = gray;
        int grayGoals = goalService.getTeamGoals(teamGray.getId());
        teamGray.setScoredGoals(grayGoals);

        TeamsStore teamsStore = TeamsStore.getInstance();
        teamsStore.storeTeam1(teamGreen);
        teamsStore.storeTeam2(teamOrange);
        teamsStore.storeTeam3(teamBlue);
        teamsStore.storeTeam4(teamGray);

    }
    public void setReceivedGoals(Team green, Team orange, Team blue, Team gray){


        TeamsStore teamsStore = TeamsStore.getInstance();
        int goalsGreen = goalService.getReceivedGoals(green.getId());
        System.out.println("Number of goals received by team Green is"+ " " + goalsGreen);
        Team teamGreen = green;
        teamGreen.setGoalsReceived(goalsGreen);
        teamsStore.storeTeam1(teamGreen);

        int goalsOrange = goalService.getReceivedGoals(orange.getId());
        System.out.println("Number of goals received by team Orange is"+ " " + goalsOrange);
        Team teamOrange = orange;
        teamOrange.setGoalsReceived(goalsOrange);
        teamsStore.storeTeam2(teamOrange);

        int goalsBlue = goalService.getReceivedGoals(blue.getId());
        System.out.println("Number of goals received by team Blue is"+ " " + goalsBlue);
        Team teamBlue = blue;
        teamBlue.setGoalsReceived(goalsBlue);
        teamsStore.storeTeam3(teamBlue);

        int goalsGray = goalService.getReceivedGoals(gray.getId());
        System.out.println("Number of goals received by team Gray is"+ " " + goalsGray);
        Team teamGray = gray;
        teamGray.setGoalsReceived(goalsGray);
        teamsStore.storeTeam4(teamGray);


        Team Green = teamsStore.getTeam1();
        Team Orange = teamsStore.getTeam2();
        Team Blue = teamsStore.getTeam3();
        Team Gray = teamsStore.getTeam4();
        System.out.println("After storing and geting, green scored" + " "  + Green.getScoredGoals()  + " " + "and recieved" + Green.getGoalsReceived());
        System.out.println("After storing and geting, orange scored" + " "  + Orange.getScoredGoals()  + " " + "and recieved" + Orange.getGoalsReceived());
        System.out.println("After storing and geting, blue scored" + " "  + Blue.getScoredGoals()  + " " + "and recieved" + Blue.getGoalsReceived());
        System.out.println("After storing and geting, gray scored" + " "  + Gray.getScoredGoals()  + " " + "and recieved" + Gray.getGoalsReceived());



    }

    //substract the scored goals from the recieved ones, on every team
    public void substractTheGoals(Team green, Team orange, Team blue, Team gray){
        Team Green = green;
        Green.setGoalsSituation(Green.getScoredGoals()-Green.getGoalsReceived());
        Team Orange = orange;
        Orange.setGoalsSituation(Orange.getScoredGoals()-Orange.getGoalsReceived());
        Team Blue = blue;
        Blue.setGoalsSituation(Blue.getScoredGoals()-Blue.getGoalsReceived());
        Team Gray = gray;
        Gray.setGoalsSituation(Gray.getScoredGoals()-Gray.getGoalsReceived());

        TeamsStore teamsStore = TeamsStore.getInstance();
        teamsStore.storeTeam1(Green);
        teamsStore.storeTeam2(Orange);
        teamsStore.storeTeam3(Blue);
        teamsStore.storeTeam4(Gray);
    }

















}
