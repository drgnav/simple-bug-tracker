package ru.drgnav.wellink.bugtracker.repository;

import java.util.List;

import ru.drgnav.wellink.bugtracker.dto.BugDTO;

public interface CustomBugRepository {
	List<BugDTO> findAllMatching(BugDTO bug);
}
