package com.prodyna.pac.conference.events.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
	private Set<User> speakers = new HashSet<User>();

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
	public Set<User> getSpeakers() {
		return speakers;
	}

	/**
	 * @param speakers
	 *            the speaker to set
	 */
	public void setSpeakers(Set<User> speakers) {
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

}
