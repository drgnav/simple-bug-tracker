package ru.drgnav.wellink.bugtracker.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import ru.drgnav.wellink.bugtracker.controller.ToolBarController.Tabs;
import ru.drgnav.wellink.bugtracker.dto.BugDTO;
import ru.drgnav.wellink.bugtracker.service.BugService;
import ru.drgnav.wellink.bugtracker.service.BugStateService;
import ru.drgnav.wellink.bugtracker.service.SecurityServices;
import ru.drgnav.wellink.bugtracker.service.UserService;
import ru.drgnav.wellink.bugtracker.util.BugTrackerUtils;
import ru.drgnav.wellink.bugtracker.util.Constants;

@Component(value = "bugEdit")
@SessionScope
public class BugEditController {

	@Autowired
	private UserService userService;

	@Autowired
	private BugService bugService;

	@Autowired
	private BugStateService bugStateService;

	@Autowired
	private ToolBarController toolbar;

	@Autowired
	private SecurityServices secureService;

	@Autowired
	private BugTrackerUtils btUtils;

	private BugDTO bug;
	private Long newStateId;
	private Long executorId;

	public BugDTO getBug() {
		return bug;
	}

	public void setBug(BugDTO bug) {
		this.bug = bug;
	}

	public Long getNewStateId() {
		return newStateId;
	}

	public void setNewStateId(Long stateId) {
		newStateId = stateId;
	}

	public void stateChanged(AjaxBehaviorEvent event) {
		newStateId = Long.parseLong(event.getComponent().getAttributes().get("value").toString());
	}

	public Long getExecutorId() {
		return executorId;
	}

	public void setExecutorId(Long executorId) {
		this.executorId = executorId;
	}

	public String save() {
		if (!btUtils.isBugValidForPersist(bug)) {
			return "";
		}
		bugService.saveBug(bug, executorId, newStateId);
		return exit();
	}

	public String exit() {
		switch (toolbar.getActiveTab()) {
		case MY_BUGS:
			return Tabs.MY_BUGS.getUrl();
		case ALL_BUGS:
			return Tabs.ALL_BUGS.getUrl();
		case AUTHOR_BUGS:
			return Tabs.AUTHOR_BUGS.getUrl();
		case PROFILE:
			return Tabs.PROFILE.getUrl();
		case USERS:
			return Tabs.USERS.getUrl();
		}
		return "";
	}

	public List<SelectItem> getUsersForState() {
		return userService.getUsersForState(newStateId, true);
	}

	public List<SelectItem> getNextStates() {
		return bugService.getNextStatesByStateId(bug.getState().getId(), true);
	}
	
	public void onEditBug() {
		FacesContext facesCtx = FacesContext.getCurrentInstance();
		if (!facesCtx.isPostback() && !facesCtx.isValidationFailed()) {
			Long bugId = null;
			Map<String, String> prms = facesCtx.getExternalContext().getRequestParameterMap();
			if (prms.containsKey("bugid")) {
				bugId = Long.parseLong(prms.get("bugid"));
			}
			if (bugId != null) {
				bug = bugService.getBugById(bugId);
			} else {
				bug = new BugDTO();
				bug.setAuthor(secureService.getCurrentUser());
				btUtils.displayErrorMessage(btUtils.getMessage("bug.init.error", bugId), null);
				bug.setState(bugStateService.findByStateName(Constants.OPEN_BUG_STATE));
			}
			newStateId = bug.getState().getId();
		}
		
	}
}
