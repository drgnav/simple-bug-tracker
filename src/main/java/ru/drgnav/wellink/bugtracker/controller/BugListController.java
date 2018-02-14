package ru.drgnav.wellink.bugtracker.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import ru.drgnav.wellink.bugtracker.dto.BugDTO;
import ru.drgnav.wellink.bugtracker.dto.BugStateDTO;
import ru.drgnav.wellink.bugtracker.dto.UserDTO;
import ru.drgnav.wellink.bugtracker.service.BugService;
import ru.drgnav.wellink.bugtracker.service.SecurityServices;
import ru.drgnav.wellink.bugtracker.util.BugTrackerUtils;

@Component(value = "bugList")
@SessionScope
public class BugListController extends FilteredListController {
	private static final BugStateDTO ANY_BUG_STATE = new BugStateDTO((Long) BugTrackerUtils.ANY_BUG_STATE.getValue(),
			BugTrackerUtils.ANY_BUG_STATE.getLabel());

	{
		headerStringKey = "search.add.bug";
	}

	@Autowired
	private ToolBarController toolBar;

	@Autowired
	private BugService bugService;

	@Autowired
	private SecurityServices securityService;

	private BugDTO myBugFilter;
	private BugDTO allBugFilter;
	private BugDTO authorBugFilter;

	private BugDTO bugFilter;

	private List<BugDTO> itemList = new ArrayList<>();

	public String getBugNumber() {
		return bugFilter.getBugNumber();
	}

	public void setBugNumber(String bugNumber) {
		bugFilter.setBugNumber(bugNumber);
	}

	public String getDescription() {
		return bugFilter.getDescription();
	}

	public void setDescription(String description) {
		bugFilter.setDescription(description);
	}

	public UserDTO getAuthor() {
		return bugFilter.getAuthor();
	}

	public void setAuthor(UserDTO author) {
		bugFilter.setAuthor(author);
	}

	public UserDTO getExecutor() {
		return bugFilter.getExecutor();
	}

	public void setExecutor(UserDTO executor) {
		bugFilter.setExecutor(executor);
	}

	public BugStateDTO getState() {
		return bugFilter.getState();
	}

	public void setState(BugStateDTO state) {
		bugFilter.setState(state);
	}

	@Override
	public void search() {
		utils.clearMessage();
		initBugList();
	}

	private void initBugList() {
		itemList = bugService.findBugsMatching(bugFilter);
	}

	@PostConstruct
	@Override
	public void clearFilter() {
		utils.clearMessage();
		initFilter();
		search();
	}

	private void initFilter() {
		UserDTO currentUser = securityService.getCurrentUser();
		bugFilter = new BugDTO();
		bugFilter.setState(ANY_BUG_STATE);
		if (toolBar.isMyBugsActive()) {
			bugFilter.setExecutor(currentUser);
		} else {
			bugFilter.setExecutor(new UserDTO());
		}
		if (toolBar.isAuthorBugsActive()) {
			bugFilter.setAuthor(currentUser);
		} else {
			bugFilter.setAuthor(new UserDTO());
		}

		if (toolBar.isMyBugsActive()) {
			myBugFilter = bugFilter;
		} else if (toolBar.isAllBugsActive()) {
			allBugFilter = bugFilter;
		} else if (toolBar.isAuthorBugsActive()) {
			authorBugFilter = bugFilter;
		}
	}

	@Override
	public void addItem() {
		if(!utils.isBugValidForPersist(bugFilter)) {
			return;
		}
		bugFilter.setAuthor(securityService.getCurrentUser());
		bugFilter.setExecutor(null);
		bugService.addNewBug(bugFilter);
		clearFilter();
	}

	@Override
	public List<BugDTO> getItems() {
		return itemList;
	}

	@Override
	public int getRowsCount() {
		return itemList.size();
	}

	public void onMyBugsLoad() {
		if (myBugFilter == null) {
			initFilter();
		}
		bugFilter = myBugFilter;
		initBugList();
	}

	public void onAuthorBugsLoad() {
		if (authorBugFilter == null) {
			initFilter();
		}
		bugFilter = authorBugFilter;
		initBugList();

	}

	public void onAllBugsLoad() {
		if (allBugFilter == null) {
			initFilter();
		}
		bugFilter = allBugFilter;
		initBugList();
	}

	public boolean isDeletable(Long bugId) {
		return bugService.isBugDeletable(bugId);
	}

	public void deleteBug(Long userId) {
		bugService.deleteBug(userId);
		search();
	}

}
