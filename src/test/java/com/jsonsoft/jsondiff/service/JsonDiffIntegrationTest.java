package com.jsonsoft.jsondiff.service;

import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.jsonsoft.jsondiff.model.JsonDiff;
import com.jsonsoft.jsondiff.repository.JsonDiffRepository;

@RunWith(SpringRunner.class)
public class JsonDiffIntegrationTest {

	@InjectMocks
	JsonService jsonService;

	@Mock
	JsonDiffRepository JsonDiffRepository;

	@Before
	public void setUp() {
		JsonDiff jsonDiff = new JsonDiff();
		Optional<JsonDiff> jsonDiffOptional = Optional.of(jsonDiff);

		Mockito.when(JsonDiffRepository.findById(any(Long.class))).thenReturn(jsonDiffOptional);
	}

	@Test()
	public void someMethod() {
		jsonService.someMethod();
	}

}
