package com.gamesys.registration.model.dao;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
public class IssuerIdNumber {

    @Id
    private String value;
}
