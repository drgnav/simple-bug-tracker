package ru.drgnav.wellink.bugtracker.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.drgnav.wellink.bugtracker.dto.RoleDTO;
import ru.drgnav.wellink.bugtracker.dto.UserDTO;

@Entity
@Table(name = "\"user\"")
public class User {
	private UserDTO userDTO = new UserDTO();
    private Set<Role> roles = new HashSet<>();

    public User() {
		super();
		
	}
    
    public User(UserDTO user) {
    	this();
    	if(user != null) {
    		this.userDTO = user;
    	}
    	Set<RoleDTO> rolesDTO = user.getRoles();
    	if(rolesDTO != null) {
    		rolesDTO.forEach(r -> roles.add(new Role(r)));
    	}
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return userDTO.getId();
    }

    public void setId(Long id) {
    	userDTO.setId(id);
    }
    
    @Column(nullable=false, unique=true)
    public String getUsername() {
        return userDTO.getUsername();
    }

    public void setUsername(String username) {
        userDTO.setUsername(username);
    }

    public String getPassword() {
        return userDTO.getPassword();
    }

    public void setPassword(String password) {
        userDTO.setPassword(password);
    }

    
	@Column(name="active")
    public Boolean isActive() {
		return userDTO.isActive();
	}

	public void setActive(Boolean isActive) {
		userDTO.setActive(isActive);
	}

	@GeneratedValue(strategy=GenerationType.AUTO)
    @Column(nullable=false, updatable=false, insertable=false)
	public Date getCreationTime() {
		return userDTO.getCreationTime();
	}
	
	public void setCreationTime(Date time) {
		userDTO.setCreationTime(time);
	}

	@ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
    @Transient
    public UserDTO getDTOInstace() {
    	UserDTO user = userDTO.clone();
    	getRoles().forEach(r -> user.addRole(r.getDTOInstace()));
    	return user;
    }
}
