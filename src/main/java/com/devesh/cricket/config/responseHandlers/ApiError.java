package com.devesh.cricket.config.responseHandlers;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ApiError {
    private HttpStatus status;
    private String message;

    @Builder.Default
    private List<String> suberrors = new ArrayList<>();
}