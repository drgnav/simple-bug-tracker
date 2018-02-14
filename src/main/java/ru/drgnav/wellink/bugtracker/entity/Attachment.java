package ru.drgnav.wellink.bugtracker.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.drgnav.wellink.bugtracker.dto.AttachmentDTO;
import ru.drgnav.wellink.bugtracker.dto.AttachmentDTO.AttachmentType;

@Entity
@Table(name = "attachment")
public class Attachment {
	private AttachmentDTO attachmentDTO = new AttachmentDTO();
	private Bug bug;
	private User author;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return attachmentDTO.getId();
	}

	public void setId(Long id) {
		attachmentDTO.setId(id);
	}

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bug_id")
	public Bug getBug() {
		return bug;
	}

	public void setBug(Bug bug) {
		attachmentDTO.setBugId(bug == null ? null : bug.getId());
		this.bug = bug;
	}

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author")
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		attachmentDTO.setAuthor(author == null ? null : author.getId());
	}

	@Enumerated(EnumType.STRING)
	public AttachmentType getType() {
		return attachmentDTO.getType();
	}

	public void setType(AttachmentType type) {
		attachmentDTO.setType(type);
	}

	public byte[] getAttachment() {
		return attachmentDTO.getAttachment();
	}

	public void setAttachment(byte[] attachment) {
		attachmentDTO.setAttachment(attachment);
	}

	public Date getCreationTime() {
		return attachmentDTO.getCreationTime();
	}

	public void setCreationTime(Date creationTime) {
		attachmentDTO.setCreationTime(creationTime);
	}

	public int hashCode() {
		return attachmentDTO.hashCode();
	}

	public boolean equals(Object obj) {
		return attachmentDTO.equals(obj);
	}

    @Transient
    public AttachmentDTO getDTOInstace() {
    	return attachmentDTO.clone();
    }	
}
