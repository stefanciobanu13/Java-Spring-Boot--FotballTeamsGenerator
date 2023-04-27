package com.teamsapp.springboot.TeamsApp.controller;

import com.teamsapp.springboot.TeamsApp.entity.*;
import com.teamsapp.springboot.TeamsApp.model.*;
import com.teamsapp.springboot.TeamsApp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    @Autowired
    private SmallFinalService smallFinalService;


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
        gamesStore.resetGames();
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
        List<Team> positions = standings1.refreshStandings();

        //retrieve the teams and their position
        Team p1 = positions.get(3);
        Team p2 = positions.get(2);
        Team p3 = positions.get(1);
        Team p4 = positions.get(0);

        //pass the to models
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
        List<Team> positions = standings1.refreshStandings();

        //retrieve the teams and their position
        Team p1 = positions.get(3);
        Team p2 = positions.get(2);
        Team p3 = positions.get(1);
        Team p4 = positions.get(0);
        //pass the to models
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
        model.addAttribute("scorersT2",scorersT2);

        return "roundForm";
    }


    @GetMapping("/nextGame")
    public String nextGame(Model model){

        String viewPage = "roundForm";

        //save the current game into the database
        GamesStore gamesStore = GamesStore.getInstance();
        List<Game> theGames = gamesStore.getTheGames();
        //get the game counter
        int gameCounter = GameCounter.getTheCounter();
        Game currentGame = theGames.get(gameCounter);
        gamesStore.storeCurrentGame(currentGame,gameCounter);
        //set up the winner
        if(currentGame.getScore_team1()>currentGame.getScore_team2()){
            currentGame.setWinner(currentGame.getTeam1().getId());
        }else{
            if (currentGame.getScore_team2() > currentGame.getScore_team1()) {
                currentGame.setWinner(currentGame.getTeam2().getId());
            }
        }
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
        Game theCurrentGame = gameService.findByTeamsIdsG1(currentGame.getTeam1().getId(),currentGame.getTeam2().getId());

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
        Team teamOrange = teamsStore.getTeam2();
        Team teamBlue = teamsStore.getTeam3();
        Team teamGray = teamsStore.getTeam4();
        //set the standings
        Standings standings = new Standings(teamGreen,teamOrange,teamBlue,teamGray);
        List<Team> theTeams =  standings.refreshStandings();

        //retrieve the teams and their position
        Team p1 = standings.getP1();
        Team p2 = standings.getP2();
        Team p3 = standings.getP3();
        Team p4 = standings.getP4();
        //tie brake
        tieBrake(p1,p2);
        tieBrake(p3,p4);

        //pass the to models
        model.addAttribute("p1",p1);
        model.addAttribute("p2",p2);
        model.addAttribute("p3",p3);
        model.addAttribute("p4",p4);
        model.addAttribute("theTeams",theTeams);
        int gameCounter3 = GameCounter.getTheCounter();
        if(gameCounter3 <= 11) {

            Game nextGame = gamesStore.getTheGames().get(gameCounter3);
            List<Player> scorersT1 = nextGame.getGoalScorersT1();
            List<Player> scorersT2 = nextGame.getGoalScorersT2();
            model.addAttribute("currentGame", nextGame);
            model.addAttribute("scorersT1", scorersT1);
            model.addAttribute("scorersT2", scorersT2);
        }
        model.addAttribute("teamGreen", teamGreen);
        model.addAttribute("teamOrange", teamOrange);
        model.addAttribute("teamBlue", teamBlue);
        model.addAttribute("teamGray", teamGray);

        if(gameCounter3==12){
            viewPage= "standings";
            //tie brake
            tieBrake(p1,p2);
            tieBrake(p3,p4); //retrieve the teams and their position
            Team p1Team = standings.getP1();
            Team p2Team = standings.getP2();
            Team p3Team = standings.getP3();
            Team p4Team = standings.getP4();
            model.addAttribute("p1T",p1Team);
            model.addAttribute("p2T",p2Team);
            model.addAttribute("p3T",p3Team);
            model.addAttribute("p4T",p4Team);
            System.out.println("P2 is" + " " + p1Team.getColor());
        }

        return viewPage;
    }


    @GetMapping("/smallFinal")
    public String smallFinal(Model model){

        //retrieve the teams and their position
        Team p1 = standings.getP1();
        Team p2 = standings.getP2();
        Team p3 = standings.getP3();
        Team p4 = standings.getP4();
        //get an instance of the game Store
        GamesStore store = GamesStore.getInstance();
        //tie brake if necessary
        tieBrake(p1,p2);
        tieBrake(p3,p4);
        //add the teams to the model
        model.addAttribute("p1Team",standings.getStandings().get(0));
        model.addAttribute("p2Team",standings.getStandings().get(1));
        model.addAttribute("p3Team",standings.getStandings().get(2));
        model.addAttribute("p4Team",standings.getStandings().get(3));
        //get the round
        GamesStore gamesStore = GamesStore.getInstance();
        //configure the finals
        store.setSmallFinal(new Game(standings.getStandings().get(2),standings.getStandings().get(3),gamesStore.getGame1().getRound()));
        //get the small final game
        Game smallFinal = store.getSmallFinal();
        model.addAttribute("smallFinal",smallFinal);
        List<Player> thePlayers1 = smallFinal.getTeam1().getThePlayers();
        List<Player> thePlayers2 = smallFinal.getTeam2().getThePlayers();
        model.addAttribute("thePlayers1",thePlayers1);
        model.addAttribute("thePlayers2",thePlayers2);

        model.addAttribute("team1",smallFinal.getTeam1());
        model.addAttribute("team2",smallFinal.getTeam2());

        return "smallFinal";
    }

    @GetMapping("/bigFinal")
    public String bigFinal(Model model){
        //increase the game counter
        GameCounter.increaseCounter();

        //save the current game into the database
        GamesStore gamesStore = GamesStore.getInstance();
        Game smallFinal = gamesStore.getSmallFinal();
        //set up the winner
        if(smallFinal.getScore_team1()>smallFinal.getScore_team2()){
            smallFinal.setWinner(smallFinal.getTeam1().getId());
        }else{
            if (smallFinal.getScore_team2() > smallFinal.getScore_team1()) {
                smallFinal.setWinner(smallFinal.getTeam2().getId());
            }
        }
        //persist the small final game
        gameService.saveGame(smallFinal);
        //get the game back including id
        Game tempSmallFinal = gameService.findByRoundId(smallFinal.getRound().getId());
        //create and save the small Final instance
        SmallFinal theSmallFinal = new SmallFinal(tempSmallFinal,tempSmallFinal.getRound());
        smallFinalService.saveSmallFinal(theSmallFinal);

        //retrieve the teams and their position
        Team p1 = standings.getP1();
        Team p2 = standings.getP2();
        Team p3 = standings.getP3();
        Team p4 = standings.getP4();
        //add the teams to the model
        model.addAttribute("p1Team",standings.getStandings().get(0));
        model.addAttribute("p2Team",standings.getStandings().get(1));
        model.addAttribute("p3Team",standings.getStandings().get(2));
        model.addAttribute("p4Team",standings.getStandings().get(3));

        //configure the big final
        gamesStore.setBigFinal(new Game(standings.getStandings().get(0),standings.getStandings().get(1),smallFinal.getRound()));
        Game bigFinals = gamesStore.getBigFinal();
        model.addAttribute("currentGame",bigFinals);

        return "bigFinal";
    }

    //a method for when t1 scores in a final
    @PostMapping("/t1GoalSF")
    public String t1GoalSF(@RequestParam("scorerId") int scorerId, Model model){

        String viewPage = "smallFinal";
        //get the scorer from database
        Player scorer =playerService.getPlayer(scorerId);
        //get the currentGame and store the goal
        int gameCounter = GameCounter.getTheCounter();
        System.out.println("the game counter in  the small final is " + " " + gameCounter);
        GamesStore gamesStore =GamesStore.getInstance();
        //define a current game variable
        Game currentGame = new Game();
        if(gameCounter==12){
             currentGame = gamesStore.getSmallFinal();
            for (Player p : currentGame.getTeam1().getThePlayers()){
                if(scorer.getId() == p.getId()){
                    currentGame.goalTeam1(scorer);
                }
            }
        }else{
            if(gameCounter==13){
                viewPage = "bigFinal";
                 currentGame = gamesStore.getBigFinal();
                for (Player p : currentGame.getTeam1().getThePlayers()){
                    if(scorer.getId() == p.getId()){
                        currentGame.goalTeam1(scorer);
                    }
                }
            }
        }
        //retrieve the teams and their position
        Team p1 = standings.getP1();
        Team p2 = standings.getP2();
        Team p3 = standings.getP3();
        Team p4 = standings.getP4();
        //add the p3 and p4 teams to the model
        model.addAttribute("p1Team",standings.getStandings().get(0));
        model.addAttribute("p2Team",standings.getStandings().get(1));
        model.addAttribute("p3Team",standings.getStandings().get(2));
        model.addAttribute("p4Team",standings.getStandings().get(3));
        model.addAttribute("smallFinal",currentGame);

        return viewPage;
    }

    //a method for when t2 scores in a final
    @PostMapping("/t2GoalSF")
    public String t2GoalSF(@RequestParam("scorerId") int scorerId, Model model){

        String viewPage = "smallFinal";
        //get the scorer from database
        Player scorer =playerService.getPlayer(scorerId);
        //get the currentGame and store the goal
        int gameCounter = GameCounter.getTheCounter();
        //define a current game variable
        Game currentGame = new Game();
        GamesStore gamesStore =GamesStore.getInstance();
        if(gameCounter==12){
             currentGame = gamesStore.getSmallFinal();
            for (Player p : currentGame.getTeam2().getThePlayers()){
                if(scorer.getId() == p.getId()){
                    currentGame.goalTeam2(scorer);
                }
            }
        }else{
            if(gameCounter==13){
                viewPage = "bigFinal";
                 currentGame = gamesStore.getBigFinal();
                for (Player p : currentGame.getTeam2().getThePlayers()){
                    if(scorer.getId() == p.getId()){
                        currentGame.goalTeam2(scorer);
                    }
                }
            }
        }
        //retrieve the teams and their position
        Team p1 = standings.getP1();
        Team p2 = standings.getP2();
        Team p3 = standings.getP3();
        Team p4 = standings.getP4();
        //add the p3 and p4 teams to the model
        model.addAttribute("p1Team",standings.getStandings().get(0));
        model.addAttribute("p2Team",standings.getStandings().get(1));
        model.addAttribute("p3Team",standings.getStandings().get(2));
        model.addAttribute("p4Team",standings.getStandings().get(3));
        model.addAttribute("smallFinal",currentGame);

        return viewPage;
    }

    public void tieBrake(Team p1,Team p2){

        if (p1.getPoints() == p2.getPoints()) {
            List<Game> wins = gameService.getWinsOfTeam1VsTeam2(p1.getId(),p2.getId());
            ArrayList<Game>theWins = (ArrayList<Game>) wins;
            int p1Wins = 0;
            int p2Wins =0;
            for(Game g:theWins) {
                if (g.getWinner() == p1.getId()) {
                    p1Wins++;
                }
                else{
                    if(g.getWinner()==p2.getId()){
                        p2Wins++;
                    }
                }
            }
            if(p2Wins > p1Wins){
                standings.setP1(p2);
                standings.setP2(p1);
            }else{
                if(p1Wins == p2Wins){
                    if (p2.getGoalsSituation() > p1.getGoalsSituation()) {
                        standings.setP1(p2);
                        standings.setP2(p1);
                    }
                    else {
                        if (p1.getGoalsSituation() == p2.getGoalsSituation()) {
                            if (p2.getScoredGoals() > p1.getScoredGoals()) {
                                standings.setP1(p2);
                                standings.setP2(p1);
                            }
                        }
                    }
                }
            }
        }

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
        Team teamGreen = green;
        teamGreen.setGoalsReceived(goalsGreen);
        teamsStore.storeTeam1(teamGreen);

        int goalsOrange = goalService.getReceivedGoals(orange.getId());
        Team teamOrange = orange;
        teamOrange.setGoalsReceived(goalsOrange);
        teamsStore.storeTeam2(teamOrange);

        int goalsBlue = goalService.getReceivedGoals(blue.getId());
        Team teamBlue = blue;
        teamBlue.setGoalsReceived(goalsBlue);
        teamsStore.storeTeam3(teamBlue);

        int goalsGray = goalService.getReceivedGoals(gray.getId());
        Team teamGray = gray;
        teamGray.setGoalsReceived(goalsGray);
        teamsStore.storeTeam4(teamGray);

        Team Green = teamsStore.getTeam1();
        Team Orange = teamsStore.getTeam2();
        Team Blue = teamsStore.getTeam3();
        Team Gray = teamsStore.getTeam4();

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
