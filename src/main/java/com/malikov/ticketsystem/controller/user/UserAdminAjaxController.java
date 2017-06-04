package com.malikov.ticketsystem.controller.user;

import com.malikov.ticketsystem.dto.UserDTO;
import com.malikov.ticketsystem.service.IUserService;
import com.malikov.ticketsystem.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yurii Malikov
 */
@RestController
@RequestMapping(value = "/ajax/admin/user")
public class UserAdminAjaxController{

    @Autowired
    IUserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    // TODO: dublicatins restriction in security xml and here???
    //@PreAuthorize()
    public List<UserDTO> getByLastName(
            @RequestParam(value = "lastNameCondition") String lastNameCondition) {
        return userService.getByLastName(lastNameCondition)
                .stream()
                .map(UserUtil::asTo)
                .collect(Collectors.toList());
    }


    @GetMapping(value = "/autocomplete-by-email", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> autocompleteByEmail(@RequestParam("term") String emailMask) {
        return userService.getEmailsBy(emailMask);
    }

    @GetMapping(value = "/autocomplete-by-last-name", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> autocompleteByLastName(@RequestParam("term") String lastNameMask) {
        return userService.getLastNamesBy(lastNameMask);
    }


    @PutMapping
    // TODO: 6/1/2017 validate dto?
    public ResponseEntity updateUser(UserDTO userDTO) {
        return userService.update(userDTO) != null
                ? new ResponseEntity(HttpStatus.OK)
                : new ResponseEntity(HttpStatus.BAD_REQUEST); // TODO: 6/3/2017 check status bad request??

    }


    @PreAuthorize("hasRole('ROLE_ADMIN')") // TODO: 5/23/2017 Duplicating??
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") int id){
        return userService.delete(id)
                ? new ResponseEntity(HttpStatus.OK)
                : new ResponseEntity(HttpStatus.BAD_REQUEST); // TODO: 6/3/2017 check status bad request??
    }


}

