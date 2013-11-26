package com.prodyna.pac.conference.events.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.prodyna.pac.conference.common.model.ConferenceEntity;
import com.prodyna.pac.conference.facility.model.Location;

/**
 * Entity implementation class for Entity: Coference.
 */
@Entity
@XmlRootElement
@Table(name = "conf_conference", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@NamedQuery(name=Conference.FIND_CONFERENCE_BY_LOCATION, query="select c from Conference c where c.location=:location")
public class Conference implements ConferenceEntity {

	public static final String FIND_CONFERENCE_BY_LOCATION = "findConferenceByLocation";

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

	/** The location. */
	@NotNull
	@ManyToOne
	@JoinColumn(name="location_id")
	private Location location;

	/** The start date. */
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	/** The end date. */
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new conference.
	 */
	public Conference() {
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
	 * Gets the location.
	 * 
	 * @return the location
	 */
	public Location getLocation() {
		return this.location;
	}

	/**
	 * Sets the location.
	 * 
	 * @param location
	 *            the new location
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * Validate dates.
	 * 
	 * @return true, if successful
	 */
	@AssertTrue(message = "end-date-not-after-start-date")
	private boolean validateDates() {
		return endDate.after(startDate);
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Conference other = (Conference) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
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
		Conference conference = new Conference();
		conference.setId(id);
		conference.setName(name);
		conference.setDescription(description);
		conference.setStartDate(startDate);
		conference.setEndDate(endDate);
		conference.setLocation(location);
		return conference;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Conference [id=" + id + ", name=" + name + ", description="
				+ description + ", location=" + location + ", startDate="
				+ startDate + ", endDate=" + endDate + "]";
	}
	
}
