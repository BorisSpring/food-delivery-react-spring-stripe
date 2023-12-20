package com.main.exceptions;

import java.io.Serial;

public class ProductException extends Exception {

	@Serial
	private static final long serialVersionUID = 2100544789576950917L;
	public ProductException(String msg) {
		super(msg);
	}
}
