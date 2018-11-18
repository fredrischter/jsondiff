package com.jsonsoft.jsondiff.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

	@Test
	public void getDiffWidgetEquals() throws IOException, JsonDiffException {
		// When
		ComparisonResult diff = compareResources(full_widget, full_widget);
		
		// Then
		assertTrue(diff.isEqualSize());
		assertTrue(diff.isEquals());
		assertEquals("[]", diff.getDifference().toString());
	}

	@Test
	public void getDiffWidgetSummarized() throws IOException, JsonDiffException {
		// When
		ComparisonResult diff = compareResources(full_widget, widget_summarized);
		
		// Then
		assertFalse(diff.isEqualSize());
		assertFalse(diff.isEquals());
		assertEquals("[{\"op\":\"remove\",\"path\":\"/widget/window/width\",\"value\":500},{\"op\":\"remove\",\"path\":\"/widget/window/height\",\"value\":500},{\"op\":\"remove\",\"path\":\"/widget/image/hOffset\",\"value\":250},{\"op\":\"remove\",\"path\":\"/widget/image/vOffset\",\"value\":250},{\"op\":\"remove\",\"path\":\"/widget/text/style\",\"value\":\"bold\"},{\"op\":\"remove\",\"path\":\"/widget/text/name\",\"value\":\"text1\"},{\"op\":\"remove\",\"path\":\"/widget/text/hOffset\",\"value\":250},{\"op\":\"remove\",\"path\":\"/widget/text/vOffset\",\"value\":100}]", diff.getDifference().toString());
	}

	@Test
	public void getDiffGlossaryEquals() throws IOException, JsonDiffException {
		// When
		ComparisonResult diff = compareResources(full_glossary, full_glossary);
		
		// Then
		assertTrue(diff.isEqualSize());
		assertTrue(diff.isEquals());
		assertEquals("[]", diff.getDifference().toString());
	}

	@Test
	public void getDiffGlossaryWithoutAcronym() throws JsonDiffException, IOException {
		// When
		ComparisonResult diff = compareResources(full_glossary, glossary_without_acronym);
		
		// Then
		assertFalse(diff.isEqualSize());
		assertFalse(diff.isEquals());
		assertEquals("[{\"op\":\"remove\",\"path\":\"/glossary/GlossDiv/GlossList/GlossEntry/Acronym\",\"value\":\"SGML\"}]", diff.getDifference().toString());
	}

	@Test
	public void getDiffGlossaryUppercase() throws JsonDiffException, IOException {
		// When
		ComparisonResult diff = compareResources(full_glossary, glossary_some_upper);
		
		// Then
		assertTrue(diff.isEqualSize());
		assertFalse(diff.isEquals());
		assertEquals("[{\"op\":\"replace\",\"path\":\"/glossary/GlossDiv/GlossList/GlossEntry/GlossDef/para\",\"value\":\"A META-MARKUP LANGUAGE, USED TO CREATE MARKUP LANGUAGES SUCH AS DOCBOOK.\"},{\"op\":\"replace\",\"path\":\"/glossary/GlossDiv/GlossList/GlossEntry/GlossSee\",\"value\":\"MARKUP\"}]", diff.getDifference().toString());
	}

	private Object toString(Resource a) throws IOException {
		return IOUtils.toString(a.getInputStream());
	}

	private ComparisonResult compareResources(Resource a, Resource b) throws IOException, JsonDiffException {
		ComparisonResult diff = JsonDiff.builder().left(IOUtils.toString(a.getInputStream()))
				.right(IOUtils.toString(b.getInputStream())).build().getComparisonResult();
		return diff;
	}

}
