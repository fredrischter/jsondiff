package com.jsonsoft.jsondiff.service;

import java.io.IOException;
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
	public void getDiffWithoutLeftTest() throws JsonDiffException, IOException {
		jsonService.getDiff(EXISTING_ID);
	}

	@Test(expected = JsonDiffNotFoundException.class)
	public void getDiffNotFoundTest() throws JsonDiffException, IOException {
		jsonService.getDiff(INEXISTENT_ID);
	}

	@Test
	public void getDiffExample() throws JsonDiffException {
		//example_a.json example_b.json 
		//example_c.json
	}

	@Test
	public void getDiffWidgetEquals() throws JsonDiffException {
		//full_widget.json full_widget.json
		//equal
	}

	@Test
	public void getDiffWidgetSummarized() throws JsonDiffException {
		//full_widget.json widget_summarized.json
		//size
	}

	@Test
	public void getDiffGlossaryEquals() throws JsonDiffException {
		//full_glossary.json full_glossary.json
		//equal
	}

	@Test
	public void getDiffGlossary() throws JsonDiffException {
		//full_widget.json full_widget.json
		//equal
	}

	@Test
	public void getDiffGlossaryWithoutAcronym() throws JsonDiffException {
		//full_glossary.json glossary_without_acronym.json
		//size
	}

	@Test
	public void getDiffGlossaryUppercase() throws JsonDiffException {
		//full_glossary.json glossary_some_upper.json
		//same size, different
	}

}
