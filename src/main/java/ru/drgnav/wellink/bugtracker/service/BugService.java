package ru.drgnav.wellink.bugtracker.service;

import java.util.List;

import javax.faces.model.SelectItem;

import ru.drgnav.wellink.bugtracker.dto.BugDTO;

public interface BugService {
	
	List<BugDTO> findBugsMatching(BugDTO user);
	
	void addNewBug(BugDTO bug);

	List<BugDTO> findMainByBugNumber(BugDTO user);

	boolean isBugDeletable(Long bugId);

	void deleteBug(Long userId);

	List<SelectItem> getNextStatesByStateId(Long stateId, boolean withEmpty);

	BugDTO getBugById(Long bugId);

	BugDTO saveBug(BugDTO bug, Long executorId, Long stateId);
}
