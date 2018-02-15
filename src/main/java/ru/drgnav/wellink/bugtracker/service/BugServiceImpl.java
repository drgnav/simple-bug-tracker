package ru.drgnav.wellink.bugtracker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.drgnav.wellink.bugtracker.dto.BugDTO;
import ru.drgnav.wellink.bugtracker.dto.UserDTO;
import ru.drgnav.wellink.bugtracker.entity.Bug;
import ru.drgnav.wellink.bugtracker.entity.BugState;
import ru.drgnav.wellink.bugtracker.repository.BugRepository;
import ru.drgnav.wellink.bugtracker.repository.BugStateRepository;
import ru.drgnav.wellink.bugtracker.repository.CustomBugRepository;
import ru.drgnav.wellink.bugtracker.repository.UserRepository;
import ru.drgnav.wellink.bugtracker.util.Constants;

@Service
public class BugServiceImpl implements BugService {

	@Autowired 
	private CustomBugRepository customBugRepo;
	
	@Autowired 
	private BugRepository bugRepo;

	@Autowired 
	private SecurityServices securitySrv;

	@Autowired 
	private UserRepository userRepo;

	@Autowired 
	private BugStateRepository bugStateRepo;

	@Override
	public List<BugDTO> findBugsMatching(BugDTO bug) {
		return customBugRepo.findAllMatching(bug);
	}

	@Override
	public void addNewBug(BugDTO bugDTO) {
		BugState state = bugStateRepo.findByName(Constants.OPEN_BUG_STATE);
		Bug bug = new Bug(bugDTO);
		bug.setState(state);
		bugRepo.save(bug);
	}

	@Override
	public List<BugDTO> findMainByBugNumber(BugDTO bug) {
		List<BugDTO> list = new ArrayList<>();
		List<Bug> bugs = bug.getId() == null ? bugRepo.findMainByName(bug.getBugNumber()) :
			bugRepo.findMainByNameAndId(bug.getId(), bug.getBugNumber());
		bugs.forEach(b -> list.add(b.getDTOInstace()));
		return list;
	}

	@Override
	public boolean isBugDeletable(Long bugId) {
		Bug bug = bugRepo.findOne(bugId);
		return bug == null ? false : Constants.OPEN_BUG_STATE.equals(bug.getState().getName());
	}

	@Override
	public void deleteBug(Long bugId) {
		bugRepo.delete(bugId);
	}

	@Override
	public List<SelectItem> getNextStatesByStateId(Long stateId, boolean withEmpty) {
		List<SelectItem> list = new ArrayList<>();
		if(withEmpty) {
			list.add(new SelectItem(0L, ""));
		}
		BugState state = bugStateRepo.findOne(stateId);
		Set<BugState> states = state.getNextStates();
		if(states != null) {
			states.forEach(s -> list.add(new SelectItem(s.getId(), s.getName())));
		}
		return list;
	}

	@Override
	public BugDTO getBugById(Long bugId) {
		Bug bug = bugRepo.findOne(bugId);
		return bug == null ? null : bug.getDTOInstace();
	}

	@Override
	public BugDTO saveBug(BugDTO bugDTO, Long executorId, Long stateId) {
		Bug bug = bugRepo.findOne(bugDTO.getId());
		if(StringUtils.equals(bug.getBugNumber(), bugDTO.getBugNumber()) 
				&& StringUtils.equals(bug.getDescription(), bugDTO.getDescription())
				&& (bug.getState() != null && Objects.equals(bug.getState().getId(), stateId))
				&& (bug.getExecutor() != null && Objects.equals(bug.getExecutor().getId(), executorId))) {
			return bugDTO;
		}
		//Копируем старую запись
		Bug historyBug = new Bug(bug);
		historyBug.setParent(bug);
		historyBug.setId(null);
		UserDTO user = securitySrv.getCurrentUser();
		historyBug.setModifier(userRepo.getOne(user.getId()));
		bugRepo.save(historyBug);
		bug.setBugNumber(bugDTO.getBugNumber());
		bug.setDescription(bugDTO.getDescription());
		if(stateId != null && stateId > 0) {
			bug.setState(bugStateRepo.getOne(stateId));
		}
		if(executorId != null && executorId > 0) {
			bug.setExecutor(userRepo.getOne(executorId));
		}
		return bugRepo.save(bug).getDTOInstace();
	}

}
