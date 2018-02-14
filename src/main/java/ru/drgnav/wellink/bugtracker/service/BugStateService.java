package ru.drgnav.wellink.bugtracker.service;

import java.util.List;

import javax.faces.model.SelectItem;

import ru.drgnav.wellink.bugtracker.dto.BugStateDTO;

public interface BugStateService {
	BugStateDTO findByStateId(Long stateId);
	BugStateDTO findByStateName(String name);
	List<SelectItem> selectAllItems();
}
