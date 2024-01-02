package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exceptions.GitHubServiceException;
import com.example.demo.model.ApiError;
import com.example.demo.model.GitHubUser;
import com.example.demo.service.GithubService;

@RestController
@RequestMapping("/api")
public class GithubController {

	@Autowired
	private GithubService githubService;

	@GetMapping("/users/{username}")
	public ResponseEntity<GitHubUser> getGithubUserProfile(@PathVariable String username) {
		GitHubUser githubUserProfile = githubService.getGithubUserProfile(username);
		return ResponseEntity.ok(githubUserProfile);
	}

	@ExceptionHandler(GitHubServiceException.class)
	ResponseEntity<ApiError> handle(GitHubServiceException e){
		ApiError apiError = new ApiError(e.getMessage());
		return ResponseEntity.internalServerError().body(apiError);
	}

}