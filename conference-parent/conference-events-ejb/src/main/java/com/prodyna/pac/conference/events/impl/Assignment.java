package com.prodyna.pac.conference.events.impl;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.prodyna.pac.conference.common.model.ConferenceEntity;
import com.prodyna.pac.conference.events.model.Talk;
import com.prodyna.pac.conference.users.model.User;

/**
 * Entity implementation class for Entity: SpeakerTalkAssignment.
 */

@Entity
@XmlRootElement
@Table(name = "conf_assign")
@NamedQueries({
	@NamedQuery(name=Assignment.FIND_ASSIGNMENTS_BY_SPEAKER, query="select a from Assignment a where a.speaker=:speaker"),
	@NamedQuery(name=Assignment.FIND_ASSIGNMENTS_BY_TALK, query="select a from Assignment a where a.talk=:talk")
})
public class Assignment implements ConferenceEntity {

	public static final String FIND_ASSIGNMENTS_BY_TALK = "findAssignmentsByTalk";

	public static final String FIND_ASSIGNMENTS_BY_SPEAKER = "findAssignmentsBySpeaker";

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue
	private Long id;

	/** The speaker. */
	@NotNull
	@ManyToOne
	private User speaker;

	/** The talk. */
	@NotNull
	@ManyToOne
	private Talk talk;

	/**
	 * Instantiates a new talk.
	 */
	public Assignment() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.conference.common.model.ConferenceEntity#getId()
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the speaker.
	 * 
	 * @return the speaker
	 */
	public User getSpeaker() {
		return speaker;
	}

	/**
	 * Sets the speaker.
	 * 
	 * @param speaker
	 *            the new speaker
	 */
	public void setSpeaker(User speaker) {
		this.speaker = speaker;
	}

	/**
	 * Gets the talk.
	 * 
	 * @return the talk
	 */
	public Talk getTalk() {
		return talk;
	}

	/**
	 * Sets the talk.
	 * 
	 * @param talk
	 *            the talk to set
	 */
	public void setTalk(Talk talk) {
		this.talk = talk;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((speaker == null) ? 0 : speaker.hashCode());
		result = prime * result + ((talk == null) ? 0 : talk.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Assignment other = (Assignment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (speaker == null) {
			if (other.speaker != null)
				return false;
		} else if (!speaker.equals(other.speaker))
			return false;
		if (talk == null) {
			if (other.talk != null)
				return false;
		} else if (!talk.equals(other.talk))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Assignment assignment = new Assignment();
		assignment.setId(id);
		assignment.setSpeaker(speaker);
		assignment.setTalk(talk);
		return assignment ;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Assignment [id=" + id + ", speaker=" + speaker + ", talk="
				+ talk + "]";
	}
	

}
