package com.malikov.ticketsystem.web.controller.ticket;

import com.malikov.ticketsystem.dto.TicketDTO;
import com.malikov.ticketsystem.model.Ticket;
import com.malikov.ticketsystem.service.ITicketService;
import com.malikov.ticketsystem.util.TicketUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ITicketService ticketService;

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


    @PutMapping
    // TODO: 6/1/2017 validate dto?
    public void updateTicket(TicketDTO ticketDTO) {
        ticketService.update(ticketDTO);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id){
        ticketService.delete(id);
    }
}
