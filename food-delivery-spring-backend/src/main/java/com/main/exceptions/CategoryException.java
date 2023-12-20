package com.main.exceptions;

import java.io.Serial;

public class CategoryException extends Exception {

	@Serial
	private static final long serialVersionUID = 396701419943595606L;

	public CategoryException(String msg) {
		super(msg);
	}
}
