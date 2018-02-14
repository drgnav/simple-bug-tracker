package ru.drgnav.wellink.bugtracker.service;

import java.util.List;

import javax.faces.model.SelectItem;

import ru.drgnav.wellink.bugtracker.dto.UserDTO;
import ru.drgnav.wellink.bugtracker.entity.User;

public interface UserService {

	void save(UserDTO user);

	User findByUsername(String username);
	
	List<UserDTO> findUsersMatching(UserDTO user);
	
	boolean isUserDeletable(Long userId);

	void deleteUser(Long userId);

	List<SelectItem> getUsersForState(Long stateId, boolean withEmpty);

	UserDTO findByUserId(Long id);
}
