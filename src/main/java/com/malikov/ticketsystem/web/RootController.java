package com.malikov.ticketsystem.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RootController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model,
                        @RequestParam(value = "error", required = false) boolean error,
                        @RequestParam(value = "message", required = false) String message) {
        model.put("error", error);
        model.put("message", message);
        return "login";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root(ModelMap model,
                       @RequestParam(value = "error", required = false) boolean error,
                       @RequestParam(value = "message", required = false) String message) {
        model.put("error", error);
        model.put("message", message);
        return "login";
    }

    @RequestMapping(value = "/flights", method = RequestMethod.GET)
    public String flights(){
        return "flights";
    }

    //@RequestMapping(value = "/purchase", method = RequestMethod.GET)
    //public String purchase(){
    //    return "purchase";
    //}
}
