package com.main.exceptions;

import java.io.Serial;

public class UserException extends RuntimeException{

	@Serial
	private static final long serialVersionUID = -5184784262695625164L;

	public UserException(String msg) {
		super(msg);
	}
}
