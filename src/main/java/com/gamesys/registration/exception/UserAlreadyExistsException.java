package com.gamesys.registration.exception;

public class UserAlreadyExistsException extends GenericException {

    public UserAlreadyExistsException(String username) {
        super("User with the username - " + username + " already exists");
    }
}
