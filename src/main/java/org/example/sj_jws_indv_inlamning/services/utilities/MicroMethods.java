package org.example.sj_jws_indv_inlamning.services.utilities;

import org.example.sj_jws_indv_inlamning.exceptions.InvalidInputException;

public class MicroMethods {

    public static <T> void validateData(String resource, String field, T value) {
        if (value == null || (value instanceof String && ((String) value).isBlank())) {
            throw new InvalidInputException(resource, field, value);
        }
    }
}
