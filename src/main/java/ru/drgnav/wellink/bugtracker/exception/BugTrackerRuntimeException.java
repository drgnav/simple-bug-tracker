package ru.drgnav.wellink.bugtracker.exception;

public class BugTrackerRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 4335648903946743262L;

	public BugTrackerRuntimeException(String message, Throwable t) {
		super(message, t);
	}

	public BugTrackerRuntimeException(String message) {
		super(message);
	}
}
