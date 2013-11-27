package com.prodyna.pac.conference.web.util;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class PanelState {

	private boolean showConferences = false;
	private boolean showTalks = false;
	private boolean showLocations = false;
	private boolean showRooms = false;
	private boolean showOrganizations = false;
	private boolean showUsers = false;

	/**
	 * @return the showConferences
	 */
	public boolean isShowConferences() {
		return showConferences;
	}

	/**
	 * @param showConferences
	 *            the showConferences to set
	 */
	public void setShowConferences(boolean showConferences) {
		this.showConferences = showConferences;
	}

	/**
	 * @return the showTalks
	 */
	public boolean isShowTalks() {
		return showTalks;
	}

	/**
	 * @param showTalks
	 *            the showTalks to set
	 */
	public void setShowTalks(boolean showTalks) {
		this.showTalks = showTalks;
	}

	/**
	 * @return the showLocations
	 */
	public boolean isShowLocations() {
		return showLocations;
	}

	/**
	 * @param showLocations
	 *            the showLocations to set
	 */
	public void setShowLocations(boolean showLocations) {
		this.showLocations = showLocations;
	}

	/**
	 * @return the showRooms
	 */
	public boolean isShowRooms() {
		return showRooms;
	}

	/**
	 * @param showRooms
	 *            the showRooms to set
	 */
	public void setShowRooms(boolean showRooms) {
		this.showRooms = showRooms;
	}

	/**
	 * @return the showOrganizations
	 */
	public boolean isShowOrganizations() {
		return showOrganizations;
	}

	/**
	 * @param showOrganizations
	 *            the showOrganizations to set
	 */
	public void setShowOrganizations(boolean showOrganizations) {
		this.showOrganizations = showOrganizations;
	}

	/**
	 * @return the showUsers
	 */
	public boolean isShowUsers() {
		return showUsers;
	}

	/**
	 * @param showUsers
	 *            the showUsers to set
	 */
	public void setShowUsers(boolean showUsers) {
		this.showUsers = showUsers;
	}
}
