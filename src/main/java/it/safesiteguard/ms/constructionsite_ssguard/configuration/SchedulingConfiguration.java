package it.safesiteguard.ms.constructionsite_ssguard.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableRetry
public class SchedulingConfiguration {

    // Classe per la modifica del comportamento dello scheduler di spring
}
