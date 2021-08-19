package com.codehospitality.deeplinking;

import com.codehospitality.deeplinking.response.DeepLinkResponse;

public class DeepLinkDemo {

    public static void main(String[] args) {
        final String domainUriPrefix = "DOMAIN";
        final String link = "LINK";
        final String androidPackageName = "ANDROID";
        final String iosBundleId = "IOS";
        final String apiKey = "API_KEY";

        DeepLinkingClient client = new DeepLinkingClient(apiKey);
        final DeepLinkResponse shortLink = client.generateShortLink(domainUriPrefix, link, androidPackageName, iosBundleId);
        System.out.println(shortLink);
    }

}
