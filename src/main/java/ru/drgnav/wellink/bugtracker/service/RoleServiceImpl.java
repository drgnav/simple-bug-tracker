package ru.drgnav.wellink.bugtracker.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.drgnav.wellink.bugtracker.dto.RoleDTO;
import ru.drgnav.wellink.bugtracker.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	private RoleRepository roleRepo;

	@Override
	public List<RoleDTO> findAllRoles() {
		List<RoleDTO> roles = new ArrayList<>();
		roleRepo.findAll().forEach(r -> roles.add(r.getDTOInstace()));
		return roles;
	}

}
