package com.prodyna.pac.conference.events.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.prodyna.pac.conference.common.model.ConferenceEntity;
import com.prodyna.pac.conference.facility.model.Room;
import com.prodyna.pac.conference.users.model.User;

/**
 * Entity implementation class for Entity: Talk.
 */
@Entity
@XmlRootElement
@Table(name = "conf_talk")
@NamedQueries({
	@NamedQuery(name = Talk.FIND_TALKS_BY_ROOM, query = "select t from Talk t where t.room = :room order by t.startDate"),
	@NamedQuery(name = Talk.FIND_TALKS_BY_CONFERENCE, query = "select t from Talk t where t.conference = :conference order by t.startDate"),
	@NamedQuery(name = Talk.FIND_TALKS_BY_SPEAKER, query = "select t from Assignment a join a.talk as t where a.speaker = :speaker order by t.startDate"),
	@NamedQuery(name = Talk.FIND_SPEAKERS_FOR_TALK, query = "select s from Assignment a join a.speaker as s where a.talk = :talk") })

public class Talk implements ConferenceEntity {

	public static final String FIND_SPEAKERS_FOR_TALK = "findSpeakersForTalk";

	public static final String FIND_TALKS_BY_SPEAKER = "findTalksBySpeaker";

	public static final String FIND_TALKS_BY_ROOM = "findTalksByRoom";

	public static final String FIND_TALKS_BY_CONFERENCE = "findTalksByConference";

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue
	private Long id;

	/** The name. */
	@NotNull
	@Size(min = 3, max = 255)
	@Column(name = "name", length=255)
	private String name;

	/** The description. */
	private String description;
	
	/** The conference. */
	@NotNull
	@ManyToOne
	@JoinColumn(name="conference_id")
	private Conference conference;

	/** The room. */
	@NotNull
	@ManyToOne
	@JoinColumn(name="room_id")
	private Room room;

	/** The speaker. */
	@Transient
	private List<User> speakers = new ArrayList<User>();

	/** The start date. */
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	/** The length. */
	@NotNull
	@Min(5)
	@Max(600)
	private Integer length;

	/**
	 * Instantiates a new talk.
	 */
	public Talk() {
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
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets the description.
	 * 
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the room.
	 * 
	 * @return the room
	 */
	public Room getRoom() {
		return this.room;
	}

	/**
	 * Sets the room.
	 * 
	 * @param room
	 *            the new room
	 */
	public void setRoom(Room room) {
		this.room = room;
	}

	/**
	 * Gets the start date.
	 * 
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Sets the start date.
	 * 
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Gets the length.
	 * 
	 * @return the length
	 */
	public Integer getLength() {
		return length;
	}

	/**
	 * Sets the length.
	 * 
	 * @param length
	 *            the length to set
	 */
	public void setLength(Integer length) {
		this.length = length;
	}

	/**
	 * @return the speaker
	 */
	public List<User> getSpeakers() {
		return speakers;
	}

	/**
	 * @param speakers
	 *            the speaker to set
	 */
	public void setSpeakers(List<User> speakers) {
		this.speakers = speakers;
	}

	/**
	 * @return the conference
	 */
	public Conference getConference() {
		return conference;
	}

	/**
	 * @param conference the conference to set
	 */
	public void setConference(Conference conference) {
		this.conference = conference;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((conference == null) ? 0 : conference.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((length == null) ? 0 : length.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		result = prime * result
				+ ((speakers == null) ? 0 : speakers.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
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
		Talk other = (Talk) obj;
		if (conference == null) {
			if (other.conference != null)
				return false;
		} else if (!conference.equals(other.conference))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (length == null) {
			if (other.length != null)
				return false;
		} else if (!length.equals(other.length))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		if (speakers == null) {
			if (other.speakers != null)
				return false;
		} else if (!speakers.equals(other.speakers))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Talk talk = new Talk();
		talk.setId(id);
		talk.setName(name);
		talk.setDescription(description);
		talk.setStartDate(startDate);
		talk.setLength(length);
		talk.setRoom(room);
		talk.getSpeakers().addAll(speakers);
		return talk;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Talk [id=" + id + ", name=" + name + ", description="
				+ description + ", conference=" + conference + ", room=" + room
				+ ", speakers=" + speakers + ", startDate=" + startDate
				+ ", length=" + length + "]";
	}

}
