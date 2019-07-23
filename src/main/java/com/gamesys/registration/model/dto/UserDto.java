package com.gamesys.registration.model.dto;

import lombok.Builder;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;
import com.gamesys.registration.validation.Password;
import com.gamesys.registration.validation.PaymentCardNumber;
import com.gamesys.registration.validation.Username;

import java.time.LocalDate;

@Builder
@Value
public class UserDto {

    @Username
    private String username;

    @Password
    private String password;

    @DateTimeFormat (iso = DateTimeFormat.ISO.DATE)
    private LocalDate dob;

    @PaymentCardNumber
    private String paymentCardNumber;
}
