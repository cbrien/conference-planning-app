package com.prodyna.pac.conference.facility.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * Entity implementation class for Entity: Room
 *
 */
@Entity
@Table(name="conf_room", uniqueConstraints = @UniqueConstraint(columnNames = {"location", "name"}))
public class Room implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private long id;
	
	@NotNull
	@Length(min=3, max=256)
	@Column(name = "name")
	private String name;
	
	private String description;
	
	@Min(2)
	private int capacity;
	
	@NotNull
	@Column(name = "location")
	private Location location;
	
	public Room() {
		super();
	}   
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
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
	public int getCapacity() {
		return this.capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}   
	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
   
}
