package com.ered.tasker.domain.dto;

public record ErrorResponse (

    int status,
    String message,
    String details
){

    
}
