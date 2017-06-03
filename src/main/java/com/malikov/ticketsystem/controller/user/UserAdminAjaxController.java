package com.malikov.ticketsystem.controller.user;

import com.malikov.ticketsystem.controller.airport.AbstractAirportController;
import com.malikov.ticketsystem.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Yurii Malikov
 */
@RestController
@RequestMapping(value = "/ajax/admin/user")
public class UserAdminAjaxController extends AbstractAirportController {

    @Autowired
    IUserService userService;

    //@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    //public List<FlightManageableDTO> getAll() {
    //    return super.getAll();
    //}
    //
    @GetMapping(value = "/autocomplete-by-email", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> autocompleteAirport(@RequestParam("term") String emailMask) {
        return userService.getEmailsByEmailMask(emailMask);
    }


}

