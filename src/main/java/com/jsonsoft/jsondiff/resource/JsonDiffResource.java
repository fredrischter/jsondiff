package com.jsonsoft.jsondiff.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/v1")
public class JsonDiffResource {

	@GetMapping("/")
	public void jsonDiff() {
		
	}

	@PostMapping("/")
	public void jsonDiffPost() {
		
	}
	
}
