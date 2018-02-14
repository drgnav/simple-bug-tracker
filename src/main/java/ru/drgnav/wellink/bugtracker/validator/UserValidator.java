package ru.drgnav.wellink.bugtracker.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ru.drgnav.wellink.bugtracker.entity.User;
import ru.drgnav.wellink.bugtracker.service.UserService;

import static ru.drgnav.wellink.bugtracker.util.Constants.*;

import java.util.Arrays;

@Component
public class UserValidator implements Validator {

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		User user = (User) o;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, FIELD_USERNAME, ERROR_FIELD_NOT_EMPTY,
				new Object[] { FIELD_USERNAME });
		if (user.getUsername().length() < MIN_USERNAME_LENGTH || user.getUsername().length() > MAX_USERNAME_LENGTH) {
			errors.rejectValue(FIELD_USERNAME, ERROR_FIELD_SIZE_INVALID,
					new Object[] { FIELD_USERNAME, MIN_USERNAME_LENGTH, MAX_USERNAME_LENGTH },
					ERROR_SIZE_INVALID_MESSAGE);
		}
		if (userService.findByUsername(user.getUsername()) != null) {
			errors.rejectValue("username", ERROR_FIELD_VALUE_DUPLICATED,
					new Object[] { FIELD_USERNAME, user.getUsername() }, ERROR_DUPLICATED_MESSAGE);
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, FIELD_PASSWORD, ERROR_FIELD_NOT_EMPTY,
				new Object[] { FIELD_PASSWORD });
		if (user.getPassword().length() < MIN_PASSWORD_LENGTH || user.getPassword().length() > MAX_PASSWORD_LENGTH) {
			errors.rejectValue(FIELD_PASSWORD, ERROR_FIELD_SIZE_INVALID,
					new Object[] { FIELD_PASSWORD, MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH },
					ERROR_SIZE_INVALID_MESSAGE);
		}
	}
}