package com.malikov.ticketsystem.web.controller.airport;

import com.malikov.ticketsystem.dto.AirportDTO;
import com.malikov.ticketsystem.service.IAirportService;
import com.malikov.ticketsystem.util.AirportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yurii Malikov
 */
@RestController
@RequestMapping(value = "/ajax/admin/airport")
public class AirportAdminAjaxController{

    @Autowired
    private IAirportService airportService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    // TODO: dublicatins restriction in security xml and here???
    //@PreAuthorize()
    public ModelMap getByName(
            @RequestParam(value = "nameCondition") String nameCondition) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("data", AirportUtil.asTo(airportService.getByName(nameCondition)));
        return modelMap;
    }

    @PutMapping
    // TODO: 6/1/2017 validate dto?
    public ResponseEntity update(AirportDTO airportDTO) {
        return airportService.update(airportDTO) != null
                ? new ResponseEntity(HttpStatus.OK)
                : new ResponseEntity(HttpStatus.BAD_REQUEST); // TODO: 6/3/2017 check status bad request??

    }


    @PreAuthorize("hasRole('ROLE_ADMIN')") // TODO: 5/23/2017 Duplicating??
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") int id){
        return airportService.delete(id)
                ? new ResponseEntity(HttpStatus.OK)
                : new ResponseEntity(HttpStatus.BAD_REQUEST); // TODO: 6/3/2017 check status bad request??
    }


}

