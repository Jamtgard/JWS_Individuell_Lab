package org.example.sj_jws_indv_inlamning.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.FORBIDDEN)
public class AuthorizationDeniedException extends RuntimeException {

    private final String resource;
    private final String acceptedRole;
    private final String action;

    public AuthorizationDeniedException(String resource, String acceptedRole, String action) {
        super(String.format(
                "Denied Authorization - Only %s of %s may perform action: %s.", acceptedRole, resource, action
        ));
        this.resource = resource;
        this.acceptedRole = acceptedRole;
        this.action = action;
    }

    public String getResource() {return resource;}
    public String getAcceptedRole() {return acceptedRole;}
    public String getAction() {return action;}
}
