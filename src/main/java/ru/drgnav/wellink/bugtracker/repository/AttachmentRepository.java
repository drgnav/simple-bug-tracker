package ru.drgnav.wellink.bugtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.drgnav.wellink.bugtracker.entity.Attachment;
import ru.drgnav.wellink.bugtracker.entity.User;

public interface AttachmentRepository extends JpaRepository<Attachment, Long>{
	boolean existsByAuthor(User author);
}
