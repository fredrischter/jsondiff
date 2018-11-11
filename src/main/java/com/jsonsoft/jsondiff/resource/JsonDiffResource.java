package com.jsonsoft.jsondiff.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsonsoft.jsondiff.exception.JsonDiffException;
import com.jsonsoft.jsondiff.model.ComparisonResult;
import com.jsonsoft.jsondiff.service.JsonService;

@RestController("/v1/diff")
public class JsonDiffResource {
	
	@Autowired
	JsonService jsonService;

	@PostMapping("/{id}/left")
	public void setLeft(@RequestParam String id, @RequestBody String json) {
		jsonService.setLeft(id, json);
	}

	@PostMapping("/{id}/right")
	public void setRight(@RequestParam String id, @RequestBody String json) {
		jsonService.setRight(id, json);
	}

	@GetMapping("/{id}")
	public ComparisonResult jsonDiffPost(@RequestParam String id) throws JsonDiffException {
		return jsonService.getDiff(id);
	}

}
