package it.safesiteguard.ms.constructionsite_ssguard.security;

import java.util.List;

public class SecurityConstants {
    public static final String JWT_SECRET = "seedseedseedseedseedseedseedseedseedseedseed"; // sarà utilizzato per l'algoritmo di firma

    public static final String ISSUER ="http://siteManagementService:8081";

    public static final String THIS_MICROSERVICE ="http://siteManagementService:8081";

    public static final List<String> AUDIENCE = List.of("http://loginService:8080");


    public static final String SUBJECT = "constructionSiteMS";

    public static final String ROLE = "MICROSERVICE-COMMUNICATION";
}
