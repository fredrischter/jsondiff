package com.jsonsoft.jsondiff.model;

import javax.persistence.Entity;
import javax.persistence.Id;

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

	public ComparisonResult getComparisonResult() throws JsonDiffException {

		if (getLeft() == null) {
			throw new JsonDiffLeftNotFoundException();
		}
		
		if (getRight() == null) {
			throw new JsonDiffRightNotFoundException();
		}
		
		// TODO comparison logics
		return null;
	}
	
}
