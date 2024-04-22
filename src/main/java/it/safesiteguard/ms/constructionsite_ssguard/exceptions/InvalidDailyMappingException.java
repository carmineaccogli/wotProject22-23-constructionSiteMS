package it.safesiteguard.ms.constructionsite_ssguard.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDailyMappingException extends Exception{
}
