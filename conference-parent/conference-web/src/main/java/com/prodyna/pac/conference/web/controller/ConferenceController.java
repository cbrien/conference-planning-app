/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.prodyna.pac.conference.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.pac.conference.events.model.Conference;
import com.prodyna.pac.conference.events.model.Talk;
import com.prodyna.pac.conference.events.service.ConferenceService;
import com.prodyna.pac.conference.events.service.TalkService;
import com.prodyna.pac.conference.facility.model.Location;
import com.prodyna.pac.conference.facility.service.LocationService;
import com.prodyna.pac.conference.web.beans.ConferenceViewBean;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://sfwk.org/Documentation/WhatIsThePurposeOfTheModelAnnotation
@Model
public class ConferenceController {

    @Inject
    private FacesContext facesContext;

    @Inject
    private ConferenceService conferenceService;
    
    @Inject
    private LocationService locationService;
    
    @Inject
    private TalkService talkService;
    
    @Produces
    @Named
    private Conference conference;
    
    private UISelectItems locationSelects;
    
    @Inject
    private ConferenceViewBean conferenceView;

    @PostConstruct
    public void initController() {
        conference = new Conference();
        locationSelects = new UISelectItems();
        for (Location location : locationService.list()){
        	UISelectItem item = new UISelectItem();
        	item.setItemValue(location);
        	item.setItemLabel(location.getName());
        	locationSelects.getChildren().add(item);
        }
    }

    public List<Conference> listConferences(){
    	return conferenceService.list();
    }
    
    
    public String showConference() {
    	conferenceView.setConference(conference);
		Map<Date, List<Talk>> talkMap = conferenceView.getTalks();
		talkMap.clear();
    	List<Talk> talks = talkService.findByConference(conference);
    	Calendar cal = GregorianCalendar.getInstance();
    	for (Talk talk : talks) {
    		cal.setTime(talk.getStartDate());
    		cal.set(Calendar.HOUR, 0);
    		cal.set(Calendar.MINUTE, 0);
    		cal.set(Calendar.SECOND, 0);
    		cal.set(Calendar.MILLISECOND, 0);
    		Date dayDate = cal.getTime();
    		List<Talk> dayList = talkMap.get(dayDate);
    		if (dayList == null) {
    			dayList = new ArrayList<Talk>();
    			talkMap.put(dayDate, dayList);
    		}
    		dayList.add(talk);
    	}
    	return "conference";
    }
    
    public void saveConference() throws Exception {
        try {
        	if (conference.getId() == null) {
        		conferenceService.add(conference);
                FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Created!", "Add conference successful");
                facesContext.addMessage(null, m);
        	}
        	else {
        		conferenceService.update(conference);
	            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Updated!", "Update conference successful");
	            facesContext.addMessage(null, m);
        	}
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
            facesContext.addMessage(null, m);
        }
    }

    private String getRootErrorMessage(Exception e) {
        // Default to general error message that registration failed.
        String errorMessage = "Registration failed. See server log for more information";
        if (e == null) {
            // This shouldn't happen, but return the default messages
            return errorMessage;
        }

        // Start with the exception and recurse to find the root cause
        Throwable t = e;
        while (t != null) {
            // Get the message from the Throwable class instance
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        // This is the root cause message
        return errorMessage;
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

	/**
	 * @return the locationSelects
	 */
	public UISelectItems getLocationSelects() {
		return locationSelects;
	}

	/**
	 * @param locationSelects the locationSelects to set
	 */
	public void setLocationSelects(UISelectItems locationSelects) {
		this.locationSelects = locationSelects;
	}

}
