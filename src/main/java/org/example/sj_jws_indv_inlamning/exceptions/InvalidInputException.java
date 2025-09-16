package org.example.sj_jws_indv_inlamning.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidInputException extends RuntimeException {

    private final String resource;
    private final String field;
    private final Object fieldValue;

    public InvalidInputException(String resource, String field, Object fieldValue) {
        super(String.format("Invalid input: %s - %s cannot be %s", resource, field, fieldValue));
        this.resource = resource;
        this.field = field;
        this.fieldValue = fieldValue;
    }

    public String getResource() {return resource;}
    public String getField() {return field;}
    public Object getFieldValue() {return fieldValue;}
}
