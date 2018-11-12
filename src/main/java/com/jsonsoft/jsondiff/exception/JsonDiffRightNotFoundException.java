package com.jsonsoft.jsondiff.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class JsonDiffRightNotFoundException extends JsonDiffException {

	private static final long serialVersionUID = 9147015056989348320L;
	
}
