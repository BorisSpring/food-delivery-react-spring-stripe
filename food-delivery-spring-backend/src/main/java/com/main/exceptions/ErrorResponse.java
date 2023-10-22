package com.main.exceptions;

import java.time.LocalDateTime;

public class ErrorResponse {

	private String msg;
	private LocalDateTime timestamp;
	private String description;
	
	public ErrorResponse(String msg, LocalDateTime timestamp, String description) {
		this.msg = msg;
		this.timestamp = timestamp;
		this.description = description;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
