package ru.drgnav.wellink.bugtracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.drgnav.wellink.bugtracker.entity.Bug;
import ru.drgnav.wellink.bugtracker.entity.User;

public interface BugRepository extends JpaRepository<Bug, Long>{
	
	boolean existsByAuthor(User author);
	boolean existsByExecutor(User executor);
	Bug findByBugNumber(String bugNumber);
	
	@Query("from ru.drgnav.wellink.bugtracker.entity.Bug b where b.parent is null"
			+ " and b.bugNumber = :number and b.id != :id")
	List<Bug> findMainByNameAndId(@Param("id") Long id, @Param("number") String name); 

	@Query("from ru.drgnav.wellink.bugtracker.entity.Bug b where b.parent is null"
			+ " and b.bugNumber = :number")
	List<Bug> findMainByName(@Param("number") String name); 
}
