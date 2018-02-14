package ru.drgnav.wellink.bugtracker.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.drgnav.wellink.bugtracker.dto.BugStateDTO;

@Entity
@Table(name = "bug_state")
public class BugState {
	private BugStateDTO bugStateDTO = new BugStateDTO();
	private Set<BugState> nextStates;
	private Set<Role> roles;

	public BugState() {
		super();
	}
	
	public BugState(BugStateDTO state) {
		this();
		bugStateDTO = state;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return bugStateDTO.getId();
	}

	public void setId(Long id) {
		bugStateDTO.setId(id);
	}

	public String getName() {
		return bugStateDTO.getName();
	}

	public void setName(String name) {
		bugStateDTO.setName(name);
	}

	public int hashCode() {
		return bugStateDTO.hashCode();
	}

	public boolean equals(Object obj) {
		return bugStateDTO.equals(obj);
	}
	
    @Transient
    public BugStateDTO getDTOInstace() {
    	return bugStateDTO.clone();
    }
    
	@ManyToMany
    @JoinTable(name = "state_role", joinColumns = @JoinColumn(name = "state_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@ManyToMany
    @JoinTable(name = "state_trace", joinColumns = @JoinColumn(name = "state_id"), inverseJoinColumns = @JoinColumn(name = "next_state"))
	public Set<BugState> getNextStates() {
		return nextStates;
	}

	public void setNextStates(Set<BugState> nextStates) {
		this.nextStates = nextStates;
	}
}
