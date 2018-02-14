package ru.drgnav.wellink.bugtracker.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import ru.drgnav.wellink.bugtracker.dto.RoleDTO;
import ru.drgnav.wellink.bugtracker.dto.UserDTO;
import ru.drgnav.wellink.bugtracker.service.RoleService;
import ru.drgnav.wellink.bugtracker.service.UserService;
import ru.drgnav.wellink.bugtracker.util.BugTrackerUtils;

@Component(value = "userList")
@SessionScope
public class UserListController extends FilteredListController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private BugTrackerUtils utils;

	private UserDTO userFilter = new UserDTO();

	private List<UserDTO> userList = new ArrayList<>();

	private List<String> roleIds = new ArrayList<>();

	public String getName() {
		return userFilter.getUsername();
	}

	public void setName(String name) {
		userFilter.setUsername(name);
	}

	public int getActive() {
		if (userFilter.isActive() == null) {
			return 0;
		}
		return userFilter.isActive() ? 1 : 2;
	}

	public void setActive(int active) {
		userFilter.setActive(active == 0 ? null : active == 1);
	}

	public List<SelectItem> getActiveList() {
		return utils.getActiveList(true);
	}

	public void searchUser() {
		userList = userService.findUsersMatching(userFilter);
	}

	@Override
	public String getHeaderString() {
		return utils.getMessage("seach.add.user");
	}

	@Override
	public void search() {
		utils.clearMessage();
		userList = userService.findUsersMatching(userFilter);
	}

	public List<String> getRoles() {
		return roleIds;
	}

	public void setRoles(List<String> roleIds) {
		this.roleIds = roleIds;
		Set<RoleDTO> roles = new HashSet<>();
		getSelectRoles().forEach(r -> {
			if (this.roleIds.contains(r.getId().toString())) {
				roles.add(r);
			}
		});
		userFilter.setRoles(roles);
	}

	@PostConstruct
	@Override
	public void clearFilter() {
		utils.clearMessage();
		userFilter = new UserDTO();
		List<RoleDTO> roles = getSelectRoles();
		userFilter.setRoles(new HashSet<>(roles));
		roles.forEach(r -> roleIds.add(r.getId().toString()));
		search();
	}

	@Override
	public void addItem() {
		boolean isValid = true;
		utils.clearMessage();
		if (StringUtils.isEmpty(userFilter.getUsername())) {
			utils.displayErrorMessage(utils.getMessage("username.empty"), "user_name");
			isValid = false;
		}
		if (userFilter.isActive() == null) {
			utils.displayErrorMessage(utils.getMessage("active.not.selected"), "user_active");
			isValid = false;
		}
		if (userFilter.getRoles().isEmpty()) {
			utils.displayErrorMessage(utils.getMessage("role.not.selected"), "user_roles");
			isValid = false;
		}
		if(userService.findByUsername(userFilter.getUsername()) != null) {
			utils.displayErrorMessage(utils.getMessage("user.exists"), "user_name");
			isValid = false;
		}
		if(!isValid) {
			return;
		}
		userFilter.setPassword(userFilter.getUsername());
		userService.save(userFilter);
		clearFilter();
	}

	@Override
	public List<UserDTO> getItems() {
		return userList;
	}

	@Override
	public int getRowsCount() {
		return userList.size();
	}

	public boolean isDeletable(Long userId) {
		return userService.isUserDeletable(userId);
	}

	public void deleteUser(Long userId) {
		userService.deleteUser(userId);
		search();
	}

	public void setSelectRoles(List<RoleDTO> roles) {
		userFilter.setRoles(new HashSet<>(roles));
	}

	public List<RoleDTO> getSelectRoles() {
		return roleService.findAllRoles();
	}
}
