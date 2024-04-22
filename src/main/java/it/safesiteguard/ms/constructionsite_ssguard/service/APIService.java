package it.safesiteguard.ms.constructionsite_ssguard.service;

import it.safesiteguard.ms.constructionsite_ssguard.domain.Worker;
import it.safesiteguard.ms.constructionsite_ssguard.dto.AuthorizedOperatorDTO;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;

import java.util.List;

public interface APIService {

    String APICALL_createUser(Worker worker) throws WebClientRequestException;

    List<AuthorizedOperatorDTO> APICALL_getAuthOperatorsMacAddresses() throws WebClientRequestException;
}
