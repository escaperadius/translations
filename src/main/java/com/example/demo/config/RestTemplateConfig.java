package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

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
    RestTemplate restTemplate = new RestTemplate();
    return restTemplate;
  }

}
