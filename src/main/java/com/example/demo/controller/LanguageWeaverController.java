package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exceptions.LanguageWeaverServiceException;
import com.example.demo.model.LanguageWeaverApiTokenError;
import com.example.demo.model.Translation;
import com.example.demo.service.LanguageWeaverService;

@RestController
@RequestMapping("/lang")
public class LanguageWeaverController {

	private Translation translation;

	@Autowired
	private LanguageWeaverService languageWeaverService;

	@GetMapping("/translation/{input}")
	public ResponseEntity<Translation> getTranslation(@PathVariable String input) {
		translation = languageWeaverService.getTranslation(input);
		return ResponseEntity.ok(translation);
	}

	@ExceptionHandler(LanguageWeaverServiceException.class)
	ResponseEntity<LanguageWeaverApiTokenError> handle(LanguageWeaverServiceException e) {
		LanguageWeaverApiTokenError apiTokenError = new LanguageWeaverApiTokenError(e.getMessage());
		return ResponseEntity.internalServerError().body(apiTokenError);
	}

}