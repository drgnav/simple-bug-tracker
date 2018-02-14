package ru.drgnav.wellink.bugtracker.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public final class Constants {
	private Constants() {
		super();
	}
	
	public static final String FIELD_USERNAME = "username";
	public static final String FIELD_PASSWORD = "password";
	public static final String ERROR_FIELD_NOT_EMPTY = "field.not.empty";
	public static final String ERROR_FIELD_SIZE_INVALID = "field.size.invalid";
	public static final String ERROR_FIELD_VALUE_DUPLICATED = "field.value.duplicated";
	public static final int MAX_USERNAME_LENGTH = 32;
	public static final int MIN_USERNAME_LENGTH = 3;
	public static final int MAX_PASSWORD_LENGTH = 32;
	public static final int MIN_PASSWORD_LENGTH = 5;
	public static final String ERROR_SIZE_INVALID_MESSAGE = "The size of the element is invalid";	
	public static final String ERROR_DUPLICATED_MESSAGE = "Duplicated";
	
	public static final int MAX_DATA_FETCH_ROWS = 1000;
	public static final Pageable MAX_DATA_FETCH_PAGE = new PageRequest(0, Constants.MAX_DATA_FETCH_ROWS);
	
	public static final String OPEN_BUG_STATE = "OPEN";
}
