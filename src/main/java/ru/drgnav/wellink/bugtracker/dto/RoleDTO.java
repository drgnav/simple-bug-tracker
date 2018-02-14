package ru.drgnav.wellink.bugtracker.dto;

import java.io.Serializable;

import ru.drgnav.wellink.bugtracker.exception.BugTrackerRuntimeException;

public class RoleDTO implements Serializable, Cloneable {

	private static final long serialVersionUID = 5368684783299153773L;

	private Long id;
	private String name;
	
	public RoleDTO() {
		super();
	}
	
	public RoleDTO(Long id, String name) {
		this();
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public RoleDTO clone() {
		try {
			return (RoleDTO) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new BugTrackerRuntimeException("super.clone() throws the exception", e);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		RoleDTO other = (RoleDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
