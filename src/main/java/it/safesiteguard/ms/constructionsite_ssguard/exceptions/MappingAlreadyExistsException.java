package it.safesiteguard.ms.constructionsite_ssguard.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MappingAlreadyExistsException extends Exception {

    public MappingAlreadyExistsException(String message) {
        super(message);
    }
}
