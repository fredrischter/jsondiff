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

	private static final String EXISTING_ID = "1";

	private static final String INEXISTENT_ID = "12312312";

	@InjectMocks
	JsonService jsonService;

	@Mock
	JsonDiffRepository JsonDiffRepository;

	@Before
	public void setUp() {
		Mockito.when(JsonDiffRepository.findById(EXISTING_ID)).thenReturn(Optional.of(new JsonDiff()));
	}

	@Test()
	public void setLeftTest() {
		jsonService.setLeft(EXISTING_ID, "{}");
	}

	@Test()
	public void setRightTest() {
		jsonService.setRight(EXISTING_ID, "{}");
	}

	@Test(expected = JsonDiffLeftNotFoundException.class)
	public void getDiffWithoutLeftTest() throws JsonDiffException {
		jsonService.getDiff(EXISTING_ID);
	}

	@Test(expected = JsonDiffNotFoundException.class)
	public void getDiffNotFoundTest() throws JsonDiffException {
		jsonService.getDiff(INEXISTENT_ID);
	}

}
