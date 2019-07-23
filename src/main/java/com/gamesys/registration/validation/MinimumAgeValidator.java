package com.gamesys.registration.validation;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

@Component
public class MinimumAgeValidator {

    public boolean isNotValid(LocalDate dob) {
        LocalDate now = LocalDate.now(ZoneOffset.UTC);
        return dob == null || ChronoUnit.YEARS.between(dob, now) < 18;
    }
}
