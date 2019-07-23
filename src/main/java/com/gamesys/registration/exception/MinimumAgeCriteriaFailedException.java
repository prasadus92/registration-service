package com.gamesys.registration.exception;

import java.time.LocalDate;

public class MinimumAgeCriteriaFailedException extends GenericException {

    public MinimumAgeCriteriaFailedException(LocalDate dob) {
        super("Minimum age criteria fails for the DOB - " + dob.toString());
    }
}
