package com.malikov.ticketsystem.web.controller;

import com.malikov.ticketsystem.AuthorizedUser;
import com.malikov.ticketsystem.dto.UserDTO;
import com.malikov.ticketsystem.model.TariffsDetails;
import com.malikov.ticketsystem.model.User;
import com.malikov.ticketsystem.service.ITariffsDetailsService;
import com.malikov.ticketsystem.service.IUserService;
import com.malikov.ticketsystem.util.dtoconverter.UserDTOConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Controller
public class RootController {

    private static final Logger LOG = LoggerFactory.getLogger(RootController.class);

    @Autowired
    MessageSource messageSource;

    @Autowired
    private IUserService userService;

    @Autowired
    private ITariffsDetailsService tariffsDetailsService;

    @GetMapping(value = "/flights")
    public String flights(){
        return "flights";
    }

    @GetMapping(value = "/tickets")
    public String tickets(){
        return "tickets";
    }

    @GetMapping(value = "/users")
    public String users(){
        return "users";
    }

    @GetMapping("/register")
    public String register(ModelMap model) {
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("register", true);
        return "profile";
    }

    @PostMapping("/register")
    public String saveRegister(@Valid UserDTO userDTO, BindingResult result, SessionStatus status, ModelMap model) {
        if (!result.hasErrors()) {
            try {
                User newUser =  userService.create(userDTO);
                LOG.info(newUser + " has been created");

                status.setComplete();

                return "redirect:login?message=app.registered&username=" + userDTO.getEmail();
            } catch (DataIntegrityViolationException ex) {
                result.rejectValue("email", "exception.sorryEmailIsNotFree");
            }
        }
        model.addAttribute("register", true);
        return "profile";
    }

    @GetMapping("/profile")
    public String profile(ModelMap modelMap) {
        modelMap.put("userDTO", UserDTOConverter.asTo(userService.get(AuthorizedUser.id())));
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@Valid UserDTO userDTO, BindingResult result, SessionStatus status) {
        if (!result.hasErrors()) {
            try {
                LOG.info("update " + userDTO);
                userDTO.setId(AuthorizedUser.id());
                userService.update(userDTO);
                status.setComplete();
                return "redirect:profile";
            } catch (DataIntegrityViolationException ex) {
                result.rejectValue("email", "exception.users.duplicate_email");
            }
        }
        return "profile";
    }

    @GetMapping("/tariffs")
    public String tariffs(ModelMap model) {
        model.addAttribute("tariffsDetails", tariffsDetailsService.getActive());
        return "tariffs";
    }

    @PostMapping("/tariffs")
    public void saveTariffs(@Valid TariffsDetails tariffsDetails) {
        tariffsDetailsService.update(tariffsDetails);
    }
}
