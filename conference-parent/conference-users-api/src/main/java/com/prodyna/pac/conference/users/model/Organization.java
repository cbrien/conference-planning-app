package com.prodyna.pac.conference.users.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.prodyna.pac.conference.common.model.ConferenceEntity;

/**
 * Entity implementation class for Entity: Room
 *
 */
@Entity
@Table(name="conf_org", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Organization implements ConferenceEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	@Size(min=3, max=255)
	@Column(name = "name", length=255)
	private String name;
	
	private String description;
	
	public Organization() {
		super();
	}   
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}   
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}   
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}   

}
