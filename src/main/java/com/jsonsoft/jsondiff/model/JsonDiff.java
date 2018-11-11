package com.jsonsoft.jsondiff.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class JsonDiff {
	
	@Id
	Long id;

}
