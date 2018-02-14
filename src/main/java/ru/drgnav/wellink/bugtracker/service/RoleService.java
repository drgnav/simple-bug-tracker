package ru.drgnav.wellink.bugtracker.service;

import java.util.List;

import ru.drgnav.wellink.bugtracker.dto.RoleDTO;

public interface RoleService {
	List<RoleDTO> findAllRoles();
}
