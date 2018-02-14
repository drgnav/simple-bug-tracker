package ru.drgnav.wellink.bugtracker.service;

import ru.drgnav.wellink.bugtracker.dto.UserDTO;

public interface SecurityServices {

	String findLoggedInUsername();

	void autologin(String username, String password);
	
	UserDTO getCurrentUser();

}
