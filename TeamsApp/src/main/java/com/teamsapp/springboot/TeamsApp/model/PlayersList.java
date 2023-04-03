package com.teamsapp.springboot.TeamsApp.model;


        import com.teamsapp.springboot.TeamsApp.entity.Player;
        import org.springframework.stereotype.Component;

        import java.util.ArrayList;


@Component
public class PlayersList {


    private ArrayList<Player> list = new ArrayList<Player>();


    public ArrayList<Player> getList() {
        return list;
    }

    public void setList(ArrayList<Player> list) {
        this.list = list;
    }

    public PlayersList(){}

    public PlayersList(ArrayList<Player> list) {
        super();
        this.list = list;
    }









}
