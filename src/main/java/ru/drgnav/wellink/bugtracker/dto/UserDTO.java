package ru.drgnav.wellink.bugtracker.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import ru.drgnav.wellink.bugtracker.exception.BugTrackerRuntimeException;

public class UserDTO implements Serializable, Cloneable {

	private static final long serialVersionUID = 6201986775461892059L;
	private Long id;
	private String username;
	private String password;
	private String passwordConfirm;
	private Boolean isActive;
	private Date creationTime;
	private Set<RoleDTO> roles = new HashSet<>();

	public UserDTO() {
		super();
	}

	public UserDTO(Long id, String username, String password, Boolean isActive, Set<RoleDTO> roles) {
		this();
		this.id = id;
		this.username = username;
		this.password = password;
		this.isActive = isActive;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean isActive() {
		return isActive;
	}

	public void setActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	@Override
	public UserDTO clone() {
		try {
			return (UserDTO) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new BugTrackerRuntimeException("super.clone() throws the exception", e);
		}
	}

	public Set<RoleDTO> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleDTO> roles) {
		this.roles = roles == null ? new HashSet<>() : roles;
	}

	public void addRole(RoleDTO role) {
		if (role == null) {
			return;
		}
		roles.add(role);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public String getRolesAsString() {
		String delim = null;
		StringBuilder sb = new StringBuilder();
		for (RoleDTO r : roles) {
			if (delim != null) {
				sb.append(delim);
			} else {
				delim = ", ";
			}
			sb.append(r.getName());
		}
		return sb.toString();
	}
}
