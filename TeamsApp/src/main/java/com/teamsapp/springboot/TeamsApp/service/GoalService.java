package com.teamsapp.springboot.TeamsApp.service;

import com.teamsapp.springboot.TeamsApp.entity.Goal;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GoalService {

    public void saveGoal(Goal goal);

    public List<Goal> getGoals();

    public Goal getGoal(int theId);

    public void deleteGoal(int theId);

    //a  method that retrieves the number of  goals of a team
    public int getTeamGoals(int teamId);

    public List<Goal> getGoalsBetweenTeams(int team1Id, int team2Id);

    public int getReceivedGoals(int teamId);

}
