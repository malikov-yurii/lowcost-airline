package com.malikov.ticketsystem.web.controller.user;

import com.malikov.ticketsystem.dto.UserDTO;
import com.malikov.ticketsystem.service.IUserService;
import com.malikov.ticketsystem.util.dtoconverter.UserDTOConverter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yurii Malikov
 */
@RestController
@RequestMapping(value = "/ajax/admin/user")
public class UserAdminAjaxController {

    @Autowired
    IUserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getByLastName(
            @RequestParam(value = "lastNameCondition") @SafeHtml @Size(min = 2) @NotBlank String lastNameCondition) {
        return userService.getByLastName(lastNameCondition)
                .stream()
                .map(UserDTOConverter::asTo)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/autocomplete-by-email", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> autocompleteByEmail(@RequestParam("term") @SafeHtml @Size(min = 2)
                                            @NotBlank String emailMask) {
        return userService.getEmailsByMask(emailMask);
    }

    @GetMapping(value = "/autocomplete-by-last-name", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> autocompleteByLastName(@RequestParam("term") @SafeHtml @Size(min = 2)
                                               @NotBlank String lastNameMask) {
        return userService.getLastNamesByMask(lastNameMask);
    }

    @PutMapping
    public ResponseEntity updateUser(@Valid UserDTO userDTO) {
        userService.update(userDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") long userId) {
        userService.delete(userId);
        return new ResponseEntity(HttpStatus.OK);
    }
}

