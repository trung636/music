package com.example.musicmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
public class UserController {

    @ResponseBody
    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    public String welcome() {
        return "hello world";
    }

    @ResponseBody
    @GetMapping("/check-login")
    public String checkLogin(HttpServletResponse response){
        return response.getHeader("token");
    }
    @RequestMapping(path = "/error", method = RequestMethod.GET)
    public String errorPage() {
        return "error";
    }

}
