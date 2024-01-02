package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.exceptions.GitHubServiceException;
import com.example.demo.model.GitHubUser;

@Service
@Slf4j
public class GithubService {

    @Value("${github.api.base-url}")
    private String githubApiBaseUrl;

    public GitHubUser getGithubUserProfile(String username) {
        try {
            log.info("Github API BaseUrl:" + githubApiBaseUrl);
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(githubApiBaseUrl + "/users/" + username, GitHubUser.class);
        } catch (RuntimeException e) {
            log.error("Fail to fetch github profile", e);
            throw new GitHubServiceException("Fail to fetch github profile for " + username);
        }
    }

}
