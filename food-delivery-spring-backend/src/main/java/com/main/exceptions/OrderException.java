package com.main.exceptions;

import java.io.Serial;

public class OrderException extends Exception {

	@Serial
	private static final long serialVersionUID = 1L;
	public OrderException(String msg) {
		super(msg);
	}
}
