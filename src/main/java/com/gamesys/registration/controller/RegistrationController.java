package com.gamesys.registration.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gamesys.registration.exception.MinimumAgeCriteriaFailedException;
import com.gamesys.registration.model.dto.UserDto;
import com.gamesys.registration.service.RegistrationService;
import com.gamesys.registration.validation.MinimumAgeValidator;

import javax.validation.Valid;

@RestController
@RequestMapping ("/api/v1/user")
@Api (value = "registration-service", description = "Available operations in Registration Service")
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {

    private final RegistrationService registrationService;
    private final MinimumAgeValidator minimumAgeValidator;

    @PostMapping (path = "/registration", produces = "application/json")
    @ApiOperation (value = "Register a new User")
    @ApiResponses ({
        @ApiResponse (code = 201, message = "Created"),
        @ApiResponse (code = 400, message = "Bad request"),
        @ApiResponse (code = 403, message = "Forbidden"),
        @ApiResponse (code = 409, message = "Conflict")
    })
    public ResponseEntity register(@RequestBody @Valid UserDto userDto) {
        log.info("New user registration request for username - {}", userDto.getUsername());
        if (minimumAgeValidator.isNotValid(userDto.getDob())) {
            throw new MinimumAgeCriteriaFailedException(userDto.getDob());
        }

        registrationService.register(userDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
