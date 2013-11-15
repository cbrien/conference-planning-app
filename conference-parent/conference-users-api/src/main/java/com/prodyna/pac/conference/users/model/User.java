package com.prodyna.pac.conference.users.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.prodyna.pac.conference.common.model.ConferenceEntity;
import com.prodyna.pac.conference.common.util.Constants;

/**
 * Entity implementation class for Entity: Location
 * 
 */
@Entity
@XmlRootElement
@Table(name = "conf_user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@NamedQuery(name=User.FIND_USERS_BY_ORGANIZATION, query="select u from User u where u.organization=:organization")
public class User implements ConferenceEntity {

	public static final String FIND_USERS_BY_ORGANIZATION = "findUsersByOrganization";

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Size(min=2,max=255)
	private String firstname;

	@NotNull
	@Size(min = 2, max = 255)
	private String lastname;

	@NotNull
	@Pattern(regexp = Constants.VALIDATION_PATTERN_EMAIL)
	@Column(name = "email")
	private String email;

	@Past
	private Date dateOfBirth;

	private String description;

	private String address;
	
	@ManyToOne
	@JoinColumn(name = "org_id")
	private Organization organization;

	private static final long serialVersionUID = 1L;

	public User() {
		super();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return this.address;
	}

	/**
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname
	 *            the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the dateOfBirth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth
	 *            the dateOfBirth to set
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the organization
	 */
	public Organization getOrganization() {
		return organization;
	}

	/**
	 * @param organization
	 *            the organization to set
	 */
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

}
