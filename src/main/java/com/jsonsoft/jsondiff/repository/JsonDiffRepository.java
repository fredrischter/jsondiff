package com.jsonsoft.jsondiff.repository;

import org.springframework.data.repository.CrudRepository;

import com.jsonsoft.jsondiff.model.JsonDiff;

public interface JsonDiffRepository extends CrudRepository<JsonDiff, String> {
	
}
