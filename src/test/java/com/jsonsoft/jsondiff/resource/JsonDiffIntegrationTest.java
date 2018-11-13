package com.jsonsoft.jsondiff.resource;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

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
import com.jayway.restassured.response.Response;
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
	public void httpRequestEquals() {
		String SAME_JSON = "{\"zz\":{\"aa\":\"bb\",\"cc\":[\"dd\",\"dd\"]},\\\"ww\\\":{\\\"aa\\\":\\\"bb\\\",\\\"cc\\\":[\\\"dd\\\",\\\"dd\\\"]}}";

		postLeft(SAME_JSON, "3").statusCode(HttpStatus.OK.value());
		postRight(SAME_JSON, "3").statusCode(HttpStatus.OK.value());
		get("3").body(equalTo("{}")).statusCode(HttpStatus.OK.value());
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

	private ValidatableResponseOptions<ValidatableResponse, Response> get(String id) {
		return given()
				.port(port)
			.when()
				.get("/v1/diff/"+id)
			.then();
	}
	
}
