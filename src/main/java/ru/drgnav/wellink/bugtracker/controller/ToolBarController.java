package ru.drgnav.wellink.bugtracker.controller;

import java.io.IOException;
import java.util.Collection;

import javax.faces.context.FacesContext;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import ru.drgnav.wellink.bugtracker.entity.Role.PredefinedRoles;

@Component(value = "toolbar")
@SessionScope
public class ToolBarController {

	private static final GrantedAuthority adminRole = new SimpleGrantedAuthority(PredefinedRoles.ADMINISTRATOR.name());

	public enum Tabs {
		//TODO: Заменить на идентификаторы и навигационные правила 
		MY_BUGS("/pages/view/mybuglist.xhtml?faces-redirect=true"), 
		AUTHOR_BUGS("/pages/view/authorbuglist.xhtml?faces-redirect=true"), 
		ALL_BUGS("/pages/view/allbuglist.xhtml?faces-redirect=true"), 
		USERS("/pages/view/userlist.xhtml?faces-redirect=true"), 
		PROFILE("/pages/edit/profile.xhtml?faces-redirect=true");
		
		private String url;
		private Tabs(String url) {
			this.url = url;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
	}

	private Tabs activeTab = Tabs.MY_BUGS;
	
	public Tabs getActiveTab() {
		return activeTab;
	}

	public boolean isUsersVisiable() {
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		return authorities.contains(adminRole);
	}
	
	public boolean isAddButtonVisiable() {
		return isAuthorBugsActive() || isUsersActive() || isAllBugsActive();
	}

	public boolean isUsersActive() {
		return activeTab == Tabs.USERS;
	}

	public boolean isMyBugsActive() {
		return activeTab == Tabs.MY_BUGS;
	}

	public boolean isAuthorBugsActive() {
		return activeTab == Tabs.AUTHOR_BUGS;
	}

	public boolean isAllBugsActive() {
		return activeTab == Tabs.ALL_BUGS;
	}

	public boolean isProfileActive() {
		return activeTab == Tabs.PROFILE;
	}
	
	public String usersTabClick() {
		if (isUsersActive()) {
			return "";
		}
		return Tabs.USERS.getUrl();
	}

	public String myBugsTabClick() {
		if (isMyBugsActive()) {
			return "";
		}
		return Tabs.MY_BUGS.getUrl();
	}

	public String authorBugsTabClick() {
		if (isAuthorBugsActive()) {
			return "";
		}
		return Tabs.AUTHOR_BUGS.getUrl();
	}

	public String allBugsTabClick() {
		if (isAllBugsActive()) {
			return "";
		}
		return Tabs.ALL_BUGS.getUrl();
	}

	public String profileTabClick() {
		if (isProfileActive()) {
			return "";
		}
		return Tabs.PROFILE.getUrl();
	}

	public void logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().invalidateSession();
		try {
			context.getExternalContext().redirect("/login?logout");
		} catch (IOException e) {
			
		}
	}
	
	public void onUsersLoad() {
		activeTab = Tabs.USERS;
	}

	public void onMyBugsLoad() {
		activeTab = Tabs.MY_BUGS;
	}

	public void onAuthorBugsLoad() {
		activeTab = Tabs.AUTHOR_BUGS;
	}

	public void onAllBugsLoad() {
		activeTab = Tabs.ALL_BUGS;
	}

	public void onProfileLoad() {
		activeTab = Tabs.PROFILE;
	}
}
