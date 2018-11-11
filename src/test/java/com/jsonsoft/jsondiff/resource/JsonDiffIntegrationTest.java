package com.jsonsoft.jsondiff.resource;

import static com.jayway.restassured.RestAssured.given;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

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
		given()
			.port(port)
			.body("{}")
			.contentType(ContentType.JSON)
		.when()
			.post("/v1/diff/1/left")
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void httpRequestTestRight() {
		given()
			.port(port)
			.body("{}")
			.contentType(ContentType.JSON)
		.when()
			.post("/v1/diff/1/right")
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void httpRequestTestGetDiff() {
		given()
			.port(port)
		.when()
			.get("/v1/diff/1")
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
}
