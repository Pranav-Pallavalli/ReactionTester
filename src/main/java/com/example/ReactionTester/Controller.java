package com.example.ReactionTester;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.stereotype.Controller
public class Controller {
    @RequestMapping(value="/rxntester",method = RequestMethod.GET)
    public String get(){
        return "view";
    }
}
