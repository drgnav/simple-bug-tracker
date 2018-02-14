package ru.drgnav.wellink.bugtracker.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.drgnav.wellink.bugtracker.dto.BugDTO;

@Entity
@Table(name = "bug")
public class Bug {
	private BugDTO bugDTO = new BugDTO();
	private User author;
	private User executor;
	private User modifier;
	private BugState state;
	private Bug parent;
	private List<Attachment> attachments;

	public Bug() {
		super();
	}
	
	public Bug(BugDTO bug) {
		this();
		if(bug == null) {
			return;
		}
		this.bugDTO = bug;
		author = bug.getAuthor() == null ? null : new User(bug.getAuthor());
		executor = bug.getExecutor()== null ? null : new User(bug.getExecutor());
		modifier = bug.getModifier() == null ? null : new User(bug.getModifier());
		state = bug.getState() == null ? null : new BugState(bug.getState());
	}
	
	public Bug(Bug that) {
		this.bugDTO = that.getDTOInstace();
		this.author = that.author;
		this.executor = that.executor;
		this.modifier = that.modifier;
		this.state = that.state;
		this.parent = that.parent;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return bugDTO.getId();
	}

	public void setId(Long id) {
		bugDTO.setId(id);
	}

	public String getBugNumber() {
		return bugDTO.getBugNumber();
	}

	public void setBugNumber(String bugNumber) {
		bugDTO.setBugNumber(bugNumber);
	}

	public String getDescription() {
		return bugDTO.getDescription();
	}

	public void setDescription(String description) {
		bugDTO.setDescription(description);
	}

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author")
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		bugDTO.setAuthor(author == null ? null : author.getDTOInstace());
		this.author = author;
	}

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor")
	public User getExecutor() {
		return executor;
	}

	public void setExecutor(User executor) {
		bugDTO.setExecutor(executor == null ? null : executor.getDTOInstace());
		this.executor = executor;
	}

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modifier")
	public User getModifier() {
		return modifier;
	}

	public void setModifier(User modifier) {
		bugDTO.setModifier(modifier == null ? null : modifier.getDTOInstace());
		this.modifier = modifier;
	}

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state")
	public BugState getState() {
		return state;
	}

	public void setState(BugState state) {
		bugDTO.setState(state == null ? null : state.getDTOInstace());
		this.state = state;
	}

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent")
	public Bug getParent() {
		return parent;
	}

	public void setParent(Bug parent) {
		bugDTO.setParent(parent == null ? null : parent.getDTOInstace());
		this.parent = parent;
	}

	public Date getCreationTime() {
		return bugDTO.getCreationTime();
	}

	public void setCreationTime(Date creationTime) {
		bugDTO.setCreationTime(creationTime);
	}

	public int hashCode() {
		return bugDTO.hashCode();
	}

	public boolean equals(Object obj) {
		return bugDTO.equals(obj);
	}
    
	@Transient
    public BugDTO getDTOInstace() {
    	return bugDTO.clone();
    }

	@OneToMany(fetch = FetchType.LAZY, mappedBy="bug")
	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

}
