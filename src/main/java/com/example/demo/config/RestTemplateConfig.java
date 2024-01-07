package com.example.demo.config;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.example.demo.exceptions.LanguageWeaverServiceException;
import com.example.demo.model.LanguageWeaverApiToken;
import com.example.demo.model.LanguageWeaverUserCredentials;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class RestTemplateConfig {

  @Value("${language-weaver.api.username}")
  private String username;

  @Value("${language-weaver.api.userpassword}")
  private String userpassword;

  @Value("${language-weaver.api.base-url}")
  private String apiTokenBaseUrl;

  @Bean
  public RestTemplate restTemplateBean() {
    return new RestTemplate();
  }

  @Bean
  public HttpHeaders getHttpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(getApiToken().getAccessToken());
    return headers;
  }

  private LanguageWeaverApiToken getApiToken() {

    LanguageWeaverUserCredentials userCredentials = new LanguageWeaverUserCredentials(username,
        userpassword);
    try {
      URI uri = new URI(apiTokenBaseUrl + "/v4/token/user");
      log.info("LanguageWeaver API Token Rest endpoint:" + uri.toString());
      RestTemplate restTemplate = new RestTemplate();
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
}
