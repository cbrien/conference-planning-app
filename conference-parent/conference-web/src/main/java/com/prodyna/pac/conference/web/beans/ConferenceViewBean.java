package com.prodyna.pac.conference.web.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.prodyna.pac.conference.events.model.Conference;
import com.prodyna.pac.conference.events.model.Talk;

@ManagedBean("conferenceView")
@ViewScoped
public class ConferenceViewBean {

	private Conference conference;
	
	private List<Date> dates = new ArrayList<Date>();
	
	private Map<Date, List<Talk>> talks = new HashMap<Date, List<Talk>>();

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

	/**
	 * @return the dates
	 */
	public List<Date> getDates() {
		return dates;
	}

	/**
	 * @param dates the dates to set
	 */
	public void setDates(List<Date> dates) {
		this.dates = dates;
	}

	/**
	 * @return the talks
	 */
	public Map<Date, List<Talk>> getTalks() {
		return talks;
	}

	/**
	 * @param talks the talks to set
	 */
	public void setTalks(Map<Date, List<Talk>> talks) {
		this.talks = talks;
	}
	
	
	
}
