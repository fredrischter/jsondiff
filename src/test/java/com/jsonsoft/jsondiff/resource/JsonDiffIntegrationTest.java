package com.jsonsoft.jsondiff.resource;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.response.ValidatableResponseOptions;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class JsonDiffIntegrationTest {
	
	@LocalServerPort
	private int port;
	
	@Before
	public void setUp() throws Exception {
	    RestAssured.port = port;
	}
	
	@Test
	public void httpRequestTestLeft() {
		postLeft("{}","1").statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void httpRequestTestRight() {
		postRight("{}","1").statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void httpRequestTestGetDiff() {
		get("1").statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void httpRequestEquals() throws IOException {
		//Given
		String SAME_JSON = "{\"zz\":{\"aa\":\"bb\",\"cc\":[\"dd\",\"dd\"]},\"ww\":{\"aa\":\"bb\",\"cc\":[\"dd\",\"dd\"]}}";
		postLeft(SAME_JSON, "3").statusCode(HttpStatus.OK.value());
		postRight(SAME_JSON, "3").statusCode(HttpStatus.OK.value());

		//When
		ResponseBody<?> body = getBody("3");
		ObjectMapper mapper = new ObjectMapper();
		JsonNode response = mapper.readTree(body.asString());
		
		//Then
		assertEquals("[]", response.get("difference").asText());
		assertEquals(true, response.get("equals").asBoolean());
		assertEquals(true, response.get("equalSize").asBoolean());
	}

	private ValidatableResponseOptions<ValidatableResponse, Response> postLeft(String json, String id) {
		return given()
				.port(port)
				.body(json)
				.contentType(ContentType.JSON)
			.when()
				.post("/v1/diff/"+id+"/left")
			.then()
				.statusCode(HttpStatus.OK.value());
	}

	private ValidatableResponseOptions<ValidatableResponse, Response> postRight(String json, String id) {
		return  given()
				.port(port)
				.body(json)
				.contentType(ContentType.JSON)
			.when()
				.post("/v1/diff/"+id+"/right")
			.then()
				.statusCode(HttpStatus.OK.value());
	}

	private ValidatableResponse get(String id) {
		return given()
				.port(port)
			.when()
				.get("/v1/diff/"+id)
			.then();
	}

	private ResponseBody<?> getBody(String id) {
		return given()
				.port(port)
			.when()
				.get("/v1/diff/"+id)
			.thenReturn().body();
	}
	
}
