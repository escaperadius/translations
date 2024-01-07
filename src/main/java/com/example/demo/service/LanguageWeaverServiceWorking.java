package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.exceptions.LanguageWeaverServiceException;
import com.example.demo.model.LanguageWeaverUserCredentials;
import com.example.demo.model.LanguageWeaverApiToken;
import com.example.demo.model.Translation;
import com.example.demo.model.TranslationOut;

@Slf4j
@Service
public class LanguageWeaverServiceWorking {

    @Value("${language-weaver.api.token}")
    private String apitoken;

    @Value("${language-weaver.api.base-url}")
    private String apiTokenBaseUrl;

    @Value("${language-weaver.api.username}")
    private String username;
    
    @Value("${language-weaver.api.userpassword}")    
    private String userpassword;

    @Value("${language-weaver.translation.sourceLanguageId}")    
    private String sourceLanguageId;

    @Value("${language-weaver.translation.targetLanguageId}")    
    private String targetLanguageId;
    
    @Value("${language-weaver.translation.model}")    
    private String model;   

    @Value("${language-weaver.translation.dictionaries}")    
    private String dictionaries;       

    @Autowired
    private RestTemplate restTemplate;

    public HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getApiToken().getAccessToken());
        return headers;
    }
    public LanguageWeaverApiToken getApiToken() {
		
        LanguageWeaverUserCredentials userCredentials = new LanguageWeaverUserCredentials(username, 
                                                                                          userpassword);
        try {
            URI uri = new URI(apiTokenBaseUrl + "/v4/token/user");
            log.info("LanguageWeaver API Token Rest endpoint:" + uri.toString());
            return restTemplate.postForObject(uri, userCredentials, LanguageWeaverApiToken.class);
        } catch (RuntimeException e) {
            log.error("Fail to fetch Language Weaver Api token ", e);
            throw new LanguageWeaverServiceException("Fail to fetch Language Weaver Api token for: " + username);
        } catch (URISyntaxException e) {
            log.error("Invalid URI ERROR: ", e);
            e.printStackTrace();
        }
        return null;
    }

    public Translation getTranslation(String input) {

        Translation translation = new Translation();
        translation.setSourceLanguageId(sourceLanguageId);
        translation.setTargetLanguageId(targetLanguageId);
        translation.setModel(model);
        translation.setInputFormat("PLAIN");
        translation.setSubmissionType("text");
        String[] dic = {dictionaries};
        translation.setDictionaries(dic);
        String[] in = {input};
        translation.setInput(in);
                     
        try {
            URI uri = new URI(apiTokenBaseUrl + "/v4/mt/translations/async");
            log.info("LanguageWeaver translation request endpoint: {}", translation.toString());
            HttpEntity<Translation> entity = new HttpEntity<>(translation, getHttpHeaders());
            
            translation = restTemplate.postForObject(uri, entity, Translation.class);

            if (!translation.getRequestId().isEmpty()) {
                String url = apiTokenBaseUrl + "/v4/mt/translations/async/" + translation.getRequestId() + "/content";
                HttpEntity<Void> entity2 = new HttpEntity<>(getHttpHeaders());
                
                TranslationOut out = restTemplate.exchange(url, HttpMethod.GET, entity2, TranslationOut.class).getBody();
                translation.setInput(in);
                translation.setDictionaries(dic);
                translation.setTranslation(out.getTranslation());

                return translation;
            }
                     
        } catch (RuntimeException e) {
            log.error("TP10 Fail to fetch Language Weaver Api token {}", e);
            throw new LanguageWeaverServiceException("Fail to fetch Language Weaver Api token for: " + username);
        } catch (URISyntaxException e) {
            log.error("Invalid URI ERROR: ", e);
            e.printStackTrace();
        }
        return null;
    }

}
