package com.teamsapp.springboot.TeamsApp.service;

import com.teamsapp.springboot.TeamsApp.dao.GoalDAO;
import com.teamsapp.springboot.TeamsApp.entity.Goal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoalServiceImp implements GoalService{

        private GoalDAO goalDAO;

        @Autowired
        public GoalServiceImp(GoalDAO goalDAO){
            this.goalDAO = goalDAO;
        }


    @Override
    public void saveGoal(Goal goal) {
            goalDAO.save(goal);
    }

    @Override
    public List<Goal> getGoals() {

            return goalDAO.findAll();
    }

    @Override
    public Goal getGoal(int theId) {

            Optional<Goal> optionalGoal = goalDAO.findById(theId);
            Goal tempGoal = optionalGoal.get();
        return tempGoal;
    }

    @Override
    public void deleteGoal(int theId) {
            goalDAO.deleteById(theId);
    }

    @Override
    public int getTeamGoals(int teamId) {

            int i = 0;
            List<Goal> goals = goalDAO.getTeamGoals(teamId);
            for(Goal g : goals){
                i++;
            }

        return i;
    }

    @Override
    public List<Goal> getGoalsBetweenTeams(int team1Id,  int team2Id) {

        return goalDAO.findGoalsBetweenTeams(team1Id,team2Id);
    }

    @Override
    public int getReceivedGoals(int teamId) {
            int goalsNr = 0;
            List<Goal> theGoals = goalDAO.getReceivedGoals(teamId);

            for(Goal g:theGoals){
                if(g.getTeam().getId() != teamId)
                goalsNr++;
            }
        return goalsNr;
    }
}


































