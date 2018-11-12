package com.jsonsoft.jsondiff.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class JsonDiffLeftNotFoundException extends JsonDiffException {

	private static final long serialVersionUID = 7911896511677855612L;

}
