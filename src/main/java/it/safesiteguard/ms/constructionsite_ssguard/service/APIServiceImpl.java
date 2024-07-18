package it.safesiteguard.ms.constructionsite_ssguard.service;

import it.safesiteguard.ms.constructionsite_ssguard.domain.Worker;
import it.safesiteguard.ms.constructionsite_ssguard.dto.AuthorizedOperatorDTO;
import it.safesiteguard.ms.constructionsite_ssguard.dto.UserRegistrationDTO;
import it.safesiteguard.ms.constructionsite_ssguard.event.WorkerDeletionEvent;
import it.safesiteguard.ms.constructionsite_ssguard.security.JwtUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
public class APIServiceImpl implements APIService {

    @Autowired
    WebClient createUserWebClient;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    private JwtUtilities jwtUtilities;

    /** Chiamata API a LoginMS per la creazione dello user
     *
     * @param worker
     * @return
     * @throws WebClientRequestException
     */
    public String APICALL_createUser(Worker worker) throws WebClientRequestException {

        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();

        userRegistrationDTO.setEmail(worker.getEmail());
        userRegistrationDTO.setRole(worker.getType());

        final String jwtToken = jwtUtilities.generateToken();

        return createUserWebClient.post()
                .uri(uriBuilder -> uriBuilder.path("/worker-registration").build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(userRegistrationDTO),UserRegistrationDTO.class)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError( error -> {eventPublisher.publishEvent(new WorkerDeletionEvent(this, worker));})
                .block();
    }


    /** Chiamata API a LoginMS per recuperare i macAddresses degli operatori selezionati
     *
     * @param
     * @return
     * @throws WebClientRequestException
     */
    public List<AuthorizedOperatorDTO> APICALL_getAuthOperatorsMacAddresses() throws WebClientRequestException {

        final String jwtToken = jwtUtilities.generateToken();

        return createUserWebClient.get()
                .uri(uriBuilder -> uriBuilder.path("/users/macAddresses")
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .retrieve()
                .bodyToFlux(AuthorizedOperatorDTO.class)
                .collectList()
                .block();
    }




}
