package ru.drgnav.wellink.bugtracker.dto;

import java.io.Serializable;
import java.util.Date;

import ru.drgnav.wellink.bugtracker.exception.BugTrackerRuntimeException;

public class BugDTO implements Serializable, Cloneable{
	private static final long serialVersionUID = -7539338821099893756L;
	private Long id;
	private String bugNumber;
	private String description;
	private UserDTO author;
	private UserDTO executor;
	private UserDTO modifier;
	private BugStateDTO state;
	private BugDTO parent;
	private Date creationTime;
	
	public BugDTO() {
		super();
	}

	public BugDTO(String bugNumber, String description, BugStateDTO state) {
		super();
		this.bugNumber = bugNumber;
		this.description = description;
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBugNumber() {
		return bugNumber;
	}

	public void setBugNumber(String bugNumber) {
		this.bugNumber = bugNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UserDTO getAuthor() {
		return author;
	}

	public void setAuthor(UserDTO author) {
		this.author = author;
	}

	public UserDTO getExecutor() {
		return executor;
	}

	public void setExecutor(UserDTO executor) {
		this.executor = executor;
	}

	public UserDTO getModifier() {
		return modifier;
	}

	public void setModifier(UserDTO modifier) {
		this.modifier = modifier;
	}

	public BugStateDTO getState() {
		return state;
	}

	public void setState(BugStateDTO state) {
		this.state = state;
	}

	public BugDTO getParent() {
		return parent;
	}

	public void setParent(BugDTO parent) {
		this.parent = parent;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bugNumber == null) ? 0 : bugNumber.hashCode());
		result = prime * result + ((creationTime == null) ? 0 : creationTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		BugDTO other = (BugDTO) obj;
		if (bugNumber == null) {
			if (other.bugNumber != null)
				return false;
		} else if (!bugNumber.equals(other.bugNumber))
			return false;
		if (creationTime == null) {
			if (other.creationTime != null)
				return false;
		} else if (!creationTime.equals(other.creationTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public BugDTO clone() {
		try {
			return (BugDTO) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new BugTrackerRuntimeException("super.clone() throws the exception", e);
		}
	}
}
