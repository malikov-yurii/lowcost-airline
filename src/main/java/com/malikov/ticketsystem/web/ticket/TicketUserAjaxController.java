package com.malikov.ticketsystem.web.ticket;

import com.malikov.ticketsystem.AuthorizedUser;
import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.model.Ticket;
import com.malikov.ticketsystem.model.User;
import com.malikov.ticketsystem.service.IFlightService;
import com.malikov.ticketsystem.service.ITicketService;
import com.malikov.ticketsystem.service.IUserService;
import com.malikov.ticketsystem.util.TicketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author Yurii Malikov
 */
@RestController
@RequestMapping(value = "/ajax/user/ticket")
public class TicketUserAjaxController {

    @Autowired
    ITicketService ticketService;

    @Autowired
    IFlightService flightService;

    @Autowired
    IUserService userService;

   /* @GetMapping
    //public List<FlightManageableDTO> getFilteredPage(
    public ModelMap getFilteredPage(
            @RequestParam(value = "fromDepartureDateTimeCondition", required = false) @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN) LocalDateTime fromDepartureDateTime,
            @RequestParam(value = "toDepartureDateTimeCondition", required = false) @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN) LocalDateTime toDepartureDateTime,
            @RequestParam(value = "departureAirportCondition", required = false) String departureAirportName,
            @RequestParam(value = "arrivalAirportCondition", required = false) String arrivalAirportName,
            @RequestParam(value = "draw") Integer draw,
            @RequestParam(value = "start") Integer startingFrom,
            @RequestParam(value = "length") Integer pageCapacity
    ) {
        List<FlightManageableDTO> flightManageableDTOS = super.getFilteredPageContent(departureAirportName, arrivalAirportName,
                fromDepartureDateTime, toDepartureDateTime, startingFrom, pageCapacity)

                .stream()
                .map(FlightUtil::asManageableDTO)
                .collect(Collectors.toList());
        //int totalFiltered = super.contFiltered();
        ModelMap model = new ModelMap();
        model.put("draw", draw);
        model.put("recordsTotal", flightManageableDTOS.size() + 1 + startingFrom);
        model.put("recordsFiltered", flightManageableDTOS.size() + 1 + startingFrom);
        //model.put("recordsFiltered", flightManageableDTOS.size() + 1);
        //model.put("recordsFiltered", flightManageableDTOS.size());
        model.put("data", flightManageableDTOS);
        return model;
    }*/

    //http://localhost:7777/lowcost-airline/ajax/user/purchase/create-new-booked-ticket/

    @PostMapping
    public ModelMap createNewBookedTicket(@RequestParam(value = "flightId") Long flightId,
                                          @RequestParam(value = "ticketPrice") BigDecimal ticketPrice) {

        Flight flight = flightService.get(flightId);
        User user = userService.get(AuthorizedUser.id());

        Ticket newTicket = ticketService.createNewBookedAndScheduledTask(flight, user, ticketPrice);

        ModelMap modelMap = new ModelMap();
        if(newTicket != null){
            modelMap.addAttribute("newTicket", TicketUtil.asDTO(newTicket));
        } else {
            // TODO: 5/30/2017 find better way to send error
            modelMap.addAttribute("error", "ticket can not be purchased");
        }
        return modelMap;
    }

   /* // todo Is it ok to  use body for this parameter or i should use pathvariable??
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/{id}/set-canceled")
    public ResponseEntity<String> setCanceled(@PathVariable("id") int id, @RequestParam("canceled") boolean canceled) {
        return super.setCanceled(id, canceled);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')") // TODO: 5/23/2017 Why not working preauthorize??
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        return super.delete(id);
    }
*/

/*// TODO: 5/22/2017 i don't need that
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateTimeUtil.DATE_TIME_PATTERN);//edit for the    format you need
        dateTimeFormat.setLenient(false);
        binder.registerCustomEditor(LocalDateTime.class, new CustomDateEditor(dateTimeFormat, true));
    }


*/


}

