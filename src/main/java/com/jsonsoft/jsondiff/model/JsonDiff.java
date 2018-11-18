package com.jsonsoft.jsondiff.model;

import java.io.IOException;
import java.util.EnumSet;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.DiffFlags;
import com.jsonsoft.jsondiff.exception.JsonDiffException;
import com.jsonsoft.jsondiff.exception.JsonDiffLeftNotFoundException;
import com.jsonsoft.jsondiff.exception.JsonDiffRightNotFoundException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class JsonDiff {

	@Id
	String id;

	String left;

	String right;

	public ComparisonResult getComparisonResult() throws JsonDiffException, IOException {

		if (getLeft() == null) {
			throw new JsonDiffLeftNotFoundException();
		}

		if (getRight() == null) {
			throw new JsonDiffRightNotFoundException();
		}

		boolean equalSize = getLeft().length() == getRight().length();
		
		ObjectMapper mapper = new ObjectMapper();
		EnumSet<DiffFlags> flags = DiffFlags.dontNormalizeOpIntoMoveAndCopy().clone();
		JsonNode patch = com.flipkart.zjsonpatch.JsonDiff.asJson(mapper.readTree(getLeft()), mapper.readTree(getRight()), flags);
		String difference = patch.asText();

		return ComparisonResult.builder().equals(equalSize && difference.length()==0).difference(difference).equalSize(equalSize).build();
	}

}
