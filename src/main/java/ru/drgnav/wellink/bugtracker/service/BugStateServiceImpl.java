package ru.drgnav.wellink.bugtracker.service;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.drgnav.wellink.bugtracker.dto.BugStateDTO;
import ru.drgnav.wellink.bugtracker.entity.BugState;
import ru.drgnav.wellink.bugtracker.repository.BugStateRepository;

@Service
public class BugStateServiceImpl implements BugStateService{

	@Autowired 
	private BugStateRepository bugStateRepo;
	
	@Override
	public BugStateDTO findByStateId(Long stateId) {
		BugState state = bugStateRepo.findOne(stateId);
		return state == null ? null : state.getDTOInstace();
	}

	@Override
	public List<SelectItem> selectAllItems() {
		List<SelectItem> items = new ArrayList<>();
		bugStateRepo.findAll().forEach(s -> items.add(new SelectItem(s.getId(), s.getName())));
		return items;
	}

	@Override
	public BugStateDTO findByStateName(String name) {
		BugState state = bugStateRepo.findByName(name);
		return state == null ? null : state.getDTOInstace();
	}
}
