package ru.drgnav.wellink.bugtracker.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.drgnav.wellink.bugtracker.dto.RoleDTO;

@Entity
@Table(name = "role")
public class Role {
	public enum PredefinedRoles {
		UNDEFINED, DEVELOPER, TESTER, ADMINISTRATOR;
	}
	
	private RoleDTO roleDTO = new RoleDTO(); 
    private Set<User> users;
	private Set<BugState> states;

    public Role() {
    	super();
    }
    
    public Role(RoleDTO role) {
    	this();
    	roleDTO = role;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return roleDTO.getId();
    }

    public void setId(Long id) {
    	roleDTO.setId(id);
    }

    public String getName() {
        return roleDTO.getName();
    }

    public void setName(String name) {
        roleDTO.setName(name);
    }

    @ManyToMany(mappedBy = "roles")
    public Set<User> getUsers() {
        return users;
    }

    @ManyToMany(mappedBy = "roles")
    public Set<BugState> getStates() {
        return states;
    }
    
    public void setStates(Set<BugState> states) {
    	this.states = states;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
	public int hashCode() {
		return roleDTO.hashCode();
	}

    @Override
	public boolean equals(Object obj) {
		return roleDTO.equals(obj);
	}
    
    @Transient
    public RoleDTO getDTOInstace() {
    	return roleDTO.clone();
    }
    
}
