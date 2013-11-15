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
	@Temporal(TemporalType.DATE)
	private Date startDate;

	/** The end date. */
	@NotNull
	@Temporal(TemporalType.DATE)
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

}
