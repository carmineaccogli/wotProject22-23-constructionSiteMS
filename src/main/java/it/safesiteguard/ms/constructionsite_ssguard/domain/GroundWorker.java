package it.safesiteguard.ms.constructionsite_ssguard.domain;


import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Predisposizione per inserimento di futuri attributi e per avere uno schema modulare
 * e flessibile all'aggiunta di ruoli nel contesto
 */

@Document(collection="workers")
@TypeAlias("ground_worker")
public class GroundWorker extends Worker {

}
