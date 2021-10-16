package com.santiago.micro.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(properties = { "xsuaa.uaadomain=localhost"})
public class TimeControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;
	
	@Value("${xsuaa.urlToken}")
	private String urlToken;

	@Value("${xsuaa.xsuaaBasicAuth}")
	private String xsuaaBasicAuth;

	@Before
	private String obtainAccessToken() throws Exception {
		
		HttpResponse<String> response = Unirest.post(urlToken + "/oauth/token?grant_type=client_credentials")
		  .header("Authorization", "Basic " + xsuaaBasicAuth)
		  .asString();
		
		JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(response.getBody()).get("access_token").toString();
	}

	@Test
	public void getWeatherForMadrid() throws Exception {
		final String accessToken = obtainAccessToken();
		mvc.perform(get("/time/madrid").header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

	}

}
