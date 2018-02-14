package ru.drgnav.wellink.bugtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.drgnav.wellink.bugtracker.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	public Role findByName(String name);
	
}
