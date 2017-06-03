package com.malikov.ticketsystem.controller.ticket;

import com.malikov.ticketsystem.dto.TicketDTO;
import com.malikov.ticketsystem.model.Ticket;
import com.malikov.ticketsystem.service.IFlightService;
import com.malikov.ticketsystem.service.ITicketService;
import com.malikov.ticketsystem.service.IUserService;
import com.malikov.ticketsystem.util.TicketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yurii Malikov
 */
@RestController
@RequestMapping(value = "/ajax/admin/ticket")
public class TicketAdminAjaxController {

    @Autowired
    ITicketService ticketService;

    @Autowired
    IFlightService flightService;

    @Autowired
    IUserService userService;

    @PutMapping
    // TODO: 6/1/2017 validate dto?
    public ResponseEntity updateTicket(TicketDTO ticketDTO) {
        return ticketService.update(ticketDTO) != null
                ? new ResponseEntity(HttpStatus.OK)
                : new ResponseEntity(HttpStatus.BAD_REQUEST); // TODO: 6/3/2017 check status bad request??

    }


    @PreAuthorize("hasRole('ROLE_ADMIN')") // TODO: 5/23/2017 Why not working preauthorize??
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") int id){
        return ticketService.delete(id)
                ? new ResponseEntity(HttpStatus.OK)
                : new ResponseEntity(HttpStatus.BAD_REQUEST); // TODO: 6/3/2017 check status bad request??
    }

    @GetMapping
    // TODO: 5/31/2017 not need preauthorize (in xml permit all?
    //@PreAuthorize()
    public ModelMap getFilteredPage(
            @RequestParam(value = "userEmailCondition", required = false) String userEmailCondition,
            @RequestParam(value = "draw") Integer draw,
            @RequestParam(value = "start") Integer startingFrom,
            @RequestParam(value = "length") Integer pageCapacity) {
        List<Ticket> tickets = ticketService.getByUserEmail(userEmailCondition, startingFrom, pageCapacity + 1);
        //// TODO: 6/3/2017 consider get rid of code duplication
        if (tickets.size() > pageCapacity) {
            tickets.remove(tickets.size() - 1);
        }
        ModelMap model = new ModelMap();
        model.put("draw", draw);
        int dataTableHasNextPageIndicator = startingFrom + tickets.size() + 1;

        model.put("recordsTotal", dataTableHasNextPageIndicator);
        model.put("recordsFiltered", dataTableHasNextPageIndicator);
        model.put("data", tickets.stream().map(TicketUtil::asDTO).collect(Collectors.toList()));
        return model;
    }

}
