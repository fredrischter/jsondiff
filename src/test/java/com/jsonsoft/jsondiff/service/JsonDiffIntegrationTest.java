package com.jsonsoft.jsondiff.service;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.jsonsoft.jsondiff.exception.JsonDiffException;
import com.jsonsoft.jsondiff.exception.JsonDiffLeftNotFoundException;
import com.jsonsoft.jsondiff.exception.JsonDiffNotFoundException;
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
		Mockito.when(JsonDiffRepository.findById("1")).thenReturn(Optional.of(new JsonDiff()));
		Mockito.when(JsonDiffRepository.findById("2")).thenReturn(null);
	}

	@Test()
	public void setLeftTest() {
		jsonService.setLeft("1", "{}");
	}

	@Test()
	public void setRightTest() {
		jsonService.setRight("1", "{}");
	}

	@Test(expected = JsonDiffLeftNotFoundException.class)
	public void getDiffWithoutLeftTest() throws JsonDiffException {
		jsonService.getDiff("1");
	}

	@Test(expected = JsonDiffNotFoundException.class)
	public void getDiffNotFoundTest() throws JsonDiffException {
		jsonService.getDiff("2");
	}

}
