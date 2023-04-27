package com.teamsapp.springboot.TeamsApp.service;

import com.teamsapp.springboot.TeamsApp.dao.SmallFinalDAO;
import com.teamsapp.springboot.TeamsApp.entity.SmallFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SmallFinalServiceImp implements SmallFinalService{


    @Autowired
    SmallFinalDAO smallFinalDAO;

    @Override
    public void saveSmallFinal(SmallFinal smallFinal) {
            smallFinalDAO.save(smallFinal);
    }

    @Override
    public List<SmallFinal> getSmallFinals() {
        return smallFinalDAO.findAll();
    }

    @Override
    public SmallFinal getSmallFinal(int theId) {

        Optional<SmallFinal> smallFinalOptional = smallFinalDAO.findById(theId);
        SmallFinal tempSmallFinal = smallFinalOptional.get();

        return tempSmallFinal;
    }

    @Override
    public void deleteSmallFinal(int theId) {
            smallFinalDAO.deleteById(theId);
    }
}
