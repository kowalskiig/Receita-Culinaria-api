package com.project.miinhareceita.shared.exceptions;

@SuppressWarnings("serial")
public class ForbiddenException extends RuntimeException {

	public ForbiddenException(String msg) {
		super(msg);
	}
}
