package com.gamesys.registration.model.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ErrorDto {
    private String message;
}
