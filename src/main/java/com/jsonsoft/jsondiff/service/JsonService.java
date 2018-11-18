package com.jsonsoft.jsondiff.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsonsoft.jsondiff.exception.JsonDiffException;
import com.jsonsoft.jsondiff.exception.JsonDiffNotFoundException;
import com.jsonsoft.jsondiff.model.ComparisonResult;
import com.jsonsoft.jsondiff.model.JsonDiff;
import com.jsonsoft.jsondiff.repository.JsonDiffRepository;

@Service
public class JsonService {

	@Autowired
	JsonDiffRepository jsonDiffRepository;
	
	public void setLeft(String id, String json) {
		Optional<JsonDiff> jsonDiff = jsonDiffRepository.findById(id);
		
		JsonDiff objectToSave = null;
		if (!jsonDiff.isPresent()) {
			objectToSave = JsonDiff.builder().left(json).id(id).build();
		} else {
			objectToSave = jsonDiff.get(); 
			objectToSave.setLeft(json);
		}
		
		jsonDiffRepository.save(objectToSave);
	}

	public void setRight(String id, String json) {
		Optional<JsonDiff> jsonDiff = jsonDiffRepository.findById(id);
		
		JsonDiff objectToSave = null;
		if (!jsonDiff.isPresent()) {
			objectToSave = JsonDiff.builder().right(json).id(id).build();
		} else {
			objectToSave = jsonDiff.get(); 
			objectToSave.setRight(json);
		}
		
		jsonDiffRepository.save(objectToSave);
	}

	public ComparisonResult getDiff(String id) throws JsonDiffException, IOException {
		Optional<JsonDiff> jsonDiffOptional = jsonDiffRepository.findById(id);
		if (!jsonDiffOptional.isPresent()) {
			throw new JsonDiffNotFoundException();
		}
		
		JsonDiff jsonDiff = jsonDiffOptional.get();
		
		return jsonDiff.getComparisonResult();
	}
	
	public String compare(String beforeString, String afterString) throws IOException {
		ObjectMapper jackson = new ObjectMapper(); 
		JsonNode beforeNode = jackson.readTree(beforeString); 
		JsonNode afterNode = jackson.readTree(afterString); 
		JsonNode patchNode = com.flipkart.zjsonpatch.JsonDiff.asJson(beforeNode, afterNode); 
		return patchNode.toString();
	}
}
