package com.jsonsoft.jsondiff.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import com.jsonsoft.jsondiff.exception.JsonDiffException;
import com.jsonsoft.jsondiff.exception.JsonDiffLeftNotFoundException;
import com.jsonsoft.jsondiff.exception.JsonDiffNotFoundException;
import com.jsonsoft.jsondiff.model.ComparisonResult;
import com.jsonsoft.jsondiff.model.JsonDiff;
import com.jsonsoft.jsondiff.repository.JsonDiffRepository;

import io.micrometer.core.instrument.util.IOUtils;

@RunWith(SpringRunner.class)
public class JsonDiffIntegrationTest {

	private static final String EXISTING_ID = "1";

	private static final String INEXISTENT_ID = "12312312";

	@InjectMocks
	JsonService jsonService;

	@Mock
	JsonDiffRepository JsonDiffRepository;

	@Value("classpath:static/json/example_a.json")
	Resource example_a;

	@Value("classpath:static/json/example_b.json")
	Resource example_b;

	@Value("classpath:static/json/example_c.json")
	Resource example_c;

	@Value("classpath:static/json/full_glossary.json")
	Resource full_glossary;

	@Value("classpath:static/json/full_widget.json")
	Resource full_widget;

	@Value("classpath:static/json/glossary_some_upper.json")
	Resource glossary_some_upper;

	@Value("classpath:static/json/glossary_without_acronym.json")
	Resource glossary_without_acronym;

	@Value("classpath:static/json/widget_summarized.json")
	Resource widget_summarized;
	
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
	public void getDiffExample() throws IOException, JsonDiffException {
		// When
		ComparisonResult diff = compareResources(example_a, example_b);
		
		// Then
		assertFalse(diff.isEqualSize());
		assertFalse(diff.isEquals());
		assertEquals(toString(example_c), diff.getDifference().toString());
	}

	private Object toString(Resource a) throws IOException {
		return IOUtils.toString(a.getInputStream());
	}

	private ComparisonResult compareResources(Resource a, Resource b) throws IOException, JsonDiffException {
		ComparisonResult diff = JsonDiff.builder().left(IOUtils.toString(a.getInputStream()))
				.right(IOUtils.toString(b.getInputStream())).build().getComparisonResult();
		return diff;
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
