package com.teamsapp.springboot.TeamsApp.service;


import com.teamsapp.springboot.TeamsApp.entity.SmallFinal;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SmallFinalService {


    public void saveSmallFinal(SmallFinal smallFinal);

    public List<SmallFinal> getSmallFinals();

    public SmallFinal getSmallFinal(int theId);

    public void deleteSmallFinal(int theId);


}
