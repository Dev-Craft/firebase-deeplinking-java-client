package io.github.dev_craft.firebase.deeplinking.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dev_craft.firebase.deeplinking.client.exceptions.FirebaseDeepLinkingClientException;
import io.github.dev_craft.firebase.deeplinking.client.request.DeepLinkCreationRequest;
import io.github.dev_craft.firebase.deeplinking.client.response.DeepLinkCreationResponse;
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
import java.util.Map;

public class DeepLinkingClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String apiKey;
    private final String FIREBASE_DEEP_LINKING_URL = "https://firebasedynamiclinks.googleapis.com/v1/shortLinks?key={key}";

    public DeepLinkingClient(String apiKey, boolean trustAllCertificates) {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.apiKey = apiKey;

        if (trustAllCertificates) {
            trustAllCertificates();
        }
    }

    public DeepLinkCreationResponse generateShortLink(DeepLinkCreationRequest request) {
        try {
            final HttpEntity<String> body = toJsonBody(request);
            final Map<String, Object> params = Map.of("key", apiKey);
            final ResponseEntity<String> response = restTemplate.exchange(FIREBASE_DEEP_LINKING_URL,
                                                                          HttpMethod.POST,
                                                                          body,
                                                                          String.class,
                                                                          params);
            if (response.getBody() != null) {
                return parseJson(response.getBody());
            }
        } catch (HttpClientErrorException e) {
            throw new FirebaseDeepLinkingClientException(e.getMessage());
        }
        return null;
    }

    private HttpEntity<String> toJsonBody(DeepLinkCreationRequest request) {
        try {
            final String body = objectMapper.writeValueAsString(request);
            return new HttpEntity<>(body);
        } catch (JsonProcessingException e) {
            throw new FirebaseDeepLinkingClientException(e.getMessage());
        }
    }

    private DeepLinkCreationResponse parseJson(String response) {
        try {
            return objectMapper.readValue(response, DeepLinkCreationResponse.class);
        } catch (JsonProcessingException e) {
            throw new FirebaseDeepLinkingClientException(e.getMessage());
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
