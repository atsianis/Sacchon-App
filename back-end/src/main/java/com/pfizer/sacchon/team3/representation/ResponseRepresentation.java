package com.pfizer.sacchon.team3.representation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseRepresentation<T> {
    private int status;
    private String description;
    private T data;
}
