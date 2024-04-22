package it.safesiteguard.ms.constructionsite_ssguard.utils;

public class LicenceUtil {

    public static int licencesClassification(String patente) {

        return switch (patente) {
            case "A1" -> 1;
            case "B" -> 2;
            case "C" -> 3;
            default -> 0;
        };
    }
}
