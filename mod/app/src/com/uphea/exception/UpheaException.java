package com.uphea.exception;

import jodd.exception.UncheckedException;

/**
 * Base application exception.
 */
public class UpheaException extends UncheckedException {

	public UpheaException(Throwable t) {
		super(t);
	}

	public UpheaException() {
	}

	public UpheaException(String message, Throwable t) {
		super(message, t);
	}

	public UpheaException(String message) {
		super(message);
	}
}
