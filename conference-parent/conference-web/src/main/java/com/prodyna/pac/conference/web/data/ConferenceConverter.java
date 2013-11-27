/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc. and/or its affiliates, and individual
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
package com.prodyna.pac.conference.web.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.pac.conference.events.model.Conference;
import com.prodyna.pac.conference.events.service.ConferenceService;

@Model
public class ConferenceConverter implements Converter {

    @Inject
    private ConferenceService conferenceService;

    private List<Conference> conferences;

    // @Named provides access the return value via the EL variable name "conferences" in the UI (e.g.
    // Facelets or JSP view)
    @Produces
    @Named("conferences")
    public List<Conference> getConferences() {
        return conferences;
    }

    public void onConferenceListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Conference conference) {
        retrieveAllConferencesOrderedByName();
    }

    @PostConstruct
    public void retrieveAllConferencesOrderedByName() {
        conferences = conferenceService.list();
    }
    
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		for (Conference conference : conferences) {
			if (conference.getId().equals(Long.parseLong(value))) {
				return conference;
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		return String.valueOf(((Conference)value).getId());
	}
}
