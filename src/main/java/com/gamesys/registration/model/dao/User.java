package com.gamesys.registration.model.dao;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Builder
public class User {

    @Id
    private String id;

    @Indexed
    private String username;

    private String password;

    private LocalDate dob;

    private String paymentCardNumber;
}
