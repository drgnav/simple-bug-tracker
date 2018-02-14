package ru.drgnav.wellink.bugtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.drgnav.wellink.bugtracker.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

}