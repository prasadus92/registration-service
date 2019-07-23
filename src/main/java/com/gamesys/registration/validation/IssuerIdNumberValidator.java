package com.gamesys.registration.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class IssuerIdNumberValidator {

    public boolean isValid(String issuerIdNumber) {
        return StringUtils.hasLength(issuerIdNumber) && issuerIdNumber.length() == 6 && issuerIdNumber.matches("[0-9]+");
    }
}
