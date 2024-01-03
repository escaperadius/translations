package com.example.demo.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class LanguageWeverControllerIntegrationTest {
    
	@Autowired
	protected MockMvc mockMvc;

    @Value("${language-weaver.api.username}")
    private String username;

	@Value("${language-weaver.api.userpassword}")
    private String userpassword;

	@RegisterExtension
	static WireMockExtension wireMockServer = WireMockExtension.newInstance()
		.options(wireMockConfig().dynamicPort())
		.build();

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("language-weaver.api.base-url", wireMockServer::baseUrl);
	}

	// @Test
	// void getLanguageWeaverApiToken() throws Exception {
    //     String jsonBody = String.format("{\"accessToken\": \"asd0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsI\", \"validityInSeconds\": 86400, \"tokenType\": \"Bearer\", \"expiresAt\": 153850533}");	
	// 	wireMockServer.stubFor(WireMock.post(urlEqualTo("/v4/token/user"))
	// 			.willReturn(
	// 				aResponse()
	// 					.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
	// 					.withBody(jsonBody)));

	// 	this.mockMvc.perform(get("/lang/token"))
	// 		.andExpect(status().isOk())
	// 		.andExpect(jsonPath("$.accessToken", is("asd0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsI")))
	// 		.andExpect(jsonPath("$.validityInSeconds", is(86400)))
	// 		.andExpect(jsonPath("$.tokenType", is("Bearer")))
	// 		.andExpect(jsonPath("$.expiresAt", is(153850533)));
	// }

	// @Test
	// void shouldGetFailureResponseWhenLanguageWeaverApiFailed() throws Exception {
	// 	wireMockServer.stubFor(WireMock.post(urlMatching("/v4/token/user/.*"))
	// 			.willReturn(aResponse().withStatus(500)));

	// 	String expectedError = "Fail to fetch Language Weaver Api token for: " + username;
	// 	this.mockMvc.perform(get("/lang/token}"))
	// 			.andExpect(status().isInternalServerError())
	// 			.andExpect(jsonPath("$.message", is(expectedError)));
	// }

	// @Test
	// void getLanguageTranslationTest() {
	// 	String accessToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6";
    //     String jsonBodyTranslationModel = String.format("{\"sourceLanguageId\": \"eng\", \"targetLanguageId\": \"ukr\", \"model\": \"generic\", \"input\": [\"Have a nice day\"]}");
	// 	wireMockServer.stubFor(WireMock.post(urlEqualTo("/v4/mt/translations/async"))
	// 			.willReturn(
	// 				aResponse()
	// 					.withHeader("Authorization", "Bearer " + accessToken)
	// 					.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
	// 					.withBody(jsonBodyTranslationModel)));

		// this.mockMvc.perform(get("/lang/translation"))
		// 	.andExpect(status().isOk());
			// .andExpect(jsonPath("$.sourceLanguageId", is("eng")))
			// .andExpect(jsonPath("$.targetLanguageId", is("ukr")))
			// .andExpect(jsonPath("$.model", is("generic")));
		//	.andExpect(jsonPath("$.input", is(153850533)));
//	}
}