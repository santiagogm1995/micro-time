package com.santiago.micro.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(properties = { "xsuaa.uaadomain=localhost" })
public class TimeControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Value("${xsuaa.urlToken}")
	private String urlToken;

	@Value("${xsuaa.xsuaaBasicAuth}")
	private String xsuaaBasicAuth;

	@Before
	private String obtainAccessToken() throws Exception {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = RequestBody.create("", mediaType);
		Request request = new Request.Builder().url(urlToken + "/oauth/token?grant_type=client_credentials")
				.method("POST", body)
				.addHeader("Authorization", "Basic " + xsuaaBasicAuth)
				.addHeader("Content-Type", "application/json").build();

		Response response = client.newCall(request).execute();

		JacksonJsonParser jsonParser = new JacksonJsonParser();
		return jsonParser.parseMap(response.body().string()).get("access_token").toString();
	}

	@Test
	public void getWeatherForMadrid() throws Exception {
		final String accessToken = obtainAccessToken();
		mvc.perform(get("/time/madrid").header("Authorization", "Bearer " + accessToken)
				.contentType(javax.ws.rs.core.MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(javax.ws.rs.core.MediaType.APPLICATION_JSON));

	}

}
