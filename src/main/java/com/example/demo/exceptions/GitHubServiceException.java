package com.example.demo.exceptions;

public class GitHubServiceException extends RuntimeException {

    public GitHubServiceException(String message) {
        super(message);
    }
}
