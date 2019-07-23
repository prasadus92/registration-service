package com.gamesys.registration.exception;

public class IssuerIdNumberBlockedException extends GenericException {

    public IssuerIdNumberBlockedException(String issuerIdNumber) {
        super("Issuer Identification Number already blocked - " + issuerIdNumber);
    }
}
