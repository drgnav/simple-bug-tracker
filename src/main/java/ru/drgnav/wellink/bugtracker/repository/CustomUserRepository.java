package ru.drgnav.wellink.bugtracker.repository;

import java.util.List;

import ru.drgnav.wellink.bugtracker.dto.UserDTO;

public interface CustomUserRepository {
	List<UserDTO> findAllMatching(UserDTO user);
}
