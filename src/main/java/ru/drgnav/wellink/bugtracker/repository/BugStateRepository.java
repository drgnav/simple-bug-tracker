package ru.drgnav.wellink.bugtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.drgnav.wellink.bugtracker.entity.BugState;

public interface BugStateRepository extends JpaRepository<BugState, Long> {
	BugState findByName(String name);
}
