package com.codehospitality.deeplinking;

import com.codehospitality.deeplinking.request.DeepLinkRequest;
import com.codehospitality.deeplinking.response.DeepLinkResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

public class DeepLinkingClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String apiKey;
    private final String FIREBASE_DEEP_LINKING_URL = "https://firebasedynamiclinks.googleapis.com/v1/shortLinks?key={key}";

    public DeepLinkingClient(String apiKey) {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.apiKey = apiKey;
    }

    public DeepLinkResponse generateShortLink(String domainUriPrefix, String link, String androidPackageName, String iosBundleId) {
        try {
            trustAllCertificates();
            final HttpEntity<String> body = toJsonBody(domainUriPrefix, link, androidPackageName, iosBundleId);
            final Map<String, Object> params = new HashMap<>();
            params.put("key", apiKey);
            final ResponseEntity<String> response = restTemplate.exchange(FIREBASE_DEEP_LINKING_URL, HttpMethod.POST, body, String.class, params);
            if (response.getBody() != null) {
                return fetch(response.getBody());
            }
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
        return null;
    }

    private HttpEntity<String> toJsonBody(String domainUriPrefix, String link, String androidPackageName, String iosBundleId) {
        final DeepLinkRequest request = new DeepLinkRequest(domainUriPrefix, link, androidPackageName, iosBundleId);
        final String body = toJson(request);
        return new HttpEntity<>(body);
    }

    private String toJson(DeepLinkRequest request) {
        try {
            return objectMapper.writeValueAsString(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private DeepLinkResponse fetch(String response) {
        try {
            return objectMapper.readValue(response, DeepLinkResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void trustAllCertificates() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                            throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                            throws CertificateException {
                    }
                }
        };

        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        // Create all-trusting host name verifier
        HostnameVerifier validHosts = new HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        };
        // All hosts will be valid
        HttpsURLConnection.setDefaultHostnameVerifier(validHosts);
    }
}
