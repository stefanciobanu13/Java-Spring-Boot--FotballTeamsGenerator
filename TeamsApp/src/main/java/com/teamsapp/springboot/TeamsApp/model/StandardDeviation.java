package com.teamsapp.springboot.TeamsApp.model;


import org.springframework.stereotype.Component;

@Component

public class StandardDeviation {

	
	    public static double calculateSD(double numArray[])
	    {
	        double sum = 0.0, standardDeviation = 0.0;
	        int length = numArray.length;

	        for(double num : numArray) {
	            sum += num;
	        }

	        double mean = sum/length;

	        for(double num: numArray) {
	            standardDeviation += Math.pow(num - mean, 2);
	        }

	        return Math.sqrt(standardDeviation/length);
	    }
	
}
	
	
	
	
	

