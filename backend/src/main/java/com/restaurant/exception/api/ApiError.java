package com.restaurant.exception.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError<T> {

    private String message;
    private List<T> details;

    public ApiError(String message) {
        this.message = message;
    }
}
