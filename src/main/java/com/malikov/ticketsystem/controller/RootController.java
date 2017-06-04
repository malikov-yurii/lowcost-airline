package com.malikov.ticketsystem.controller;

import com.malikov.ticketsystem.AuthorizedUser;
import com.malikov.ticketsystem.dto.UserDTO;
import com.malikov.ticketsystem.model.User;
import com.malikov.ticketsystem.service.ITariffsDetailsService;
import com.malikov.ticketsystem.service.IUserService;
import com.malikov.ticketsystem.util.UserUtil;
import com.malikov.ticketsystem.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Controller
public class RootController {

    private static final Logger LOG = LoggerFactory.getLogger(RootController.class);

    @Autowired
    IUserService userService;

    @Autowired
    private ITariffsDetailsService tariffsDetailsService;
/*
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
    }*/

    @RequestMapping(value = "/flights", method = RequestMethod.GET)
    public String flights(){
        return "flights";
    }

    @RequestMapping(value = "/tickets", method = RequestMethod.GET)
    public String tickets(){
        return "tickets";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(){
        return "users";
    }

    @RequestMapping(value = "/airports", method = RequestMethod.GET)
    public String airports(){
        return "airports";
    }

    @GetMapping("/register")
    public String register(ModelMap model) {
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("register", true);
        return "profile";
    }

    @GetMapping("/tariffs")
    public String tariffs(ModelMap model) {
        model.addAttribute("tariffsDetails", tariffsDetailsService.getActive());
        return "tariffs";
    }

    @PutMapping("/tariffs")
    public String saveTariffs(@Valid UserDTO userDTO, ModelMap model) {
        model.addAttribute("tariffsDetails", tariffsDetailsService.getActive());
        return "tariffs";
    }

    @PostMapping("/register")
    public String saveRegister(@Valid UserDTO userDTO, BindingResult result, SessionStatus status, ModelMap model) {
        if (!result.hasErrors()) {
            try {
                ValidationUtil.checkNew(userDTO);
                User newUser = UserUtil.createNewFromTo(userDTO);
                newUser =  userService.save(newUser);
                LOG.info(newUser + " has been created");

                status.setComplete();

                return "redirect:login?message=app.registered&username=" + userDTO.getEmail();
            } catch (DataIntegrityViolationException ex) {
                result.rejectValue("email", "exception.users.duplicate_email");
            }
        }
        model.addAttribute("register", true);
        return "profile";
    }

    @GetMapping("/profile")
    public String profile(ModelMap modelMap) {
        modelMap.put("userDTO", UserUtil.asTo(userService.get(AuthorizedUser.id())));
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@Valid UserDTO userDTO, BindingResult result, SessionStatus status) {
        if (!result.hasErrors()) {
            try {

                LOG.info("update " + userDTO);
                ValidationUtil.checkIdConsistent(userDTO, AuthorizedUser.id());

                userService.update(userDTO);
                status.setComplete();
                return "redirect:meals";
            } catch (DataIntegrityViolationException ex) {
                result.rejectValue("email", "exception.users.duplicate_email");
            }
        }
        return "profile";
    }
}
