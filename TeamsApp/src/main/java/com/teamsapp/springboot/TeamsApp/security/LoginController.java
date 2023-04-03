package com.teamsapp.springboot.TeamsApp.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

@GetMapping("/showLoginPage")
    public String showLoginPage(){

    return "loginForm";
}

@GetMapping("/accesDenied")
public String accesDenied(){
    return "accesDenied";
}







}
