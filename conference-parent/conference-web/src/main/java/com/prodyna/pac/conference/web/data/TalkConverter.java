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

import com.prodyna.pac.conference.events.model.Talk;
import com.prodyna.pac.conference.events.service.TalkService;

@Model
public class TalkConverter implements Converter {

    @Inject
    private TalkService talkService;

    private List<Talk> talks;

    // @Named provides access the return value via the EL variable name "talks" in the UI (e.g.
    // Facelets or JSP view)
    @Produces
    @Named("talks")
    public List<Talk> getTalks() {
        return talks;
    }

    public void onTalkListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Talk talk) {
        retrieveAllTalksOrderedByName();
    }

    @PostConstruct
    public void retrieveAllTalksOrderedByName() {
        talks = talkService.list();
    }
    
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		for (Talk talk : talks) {
			if (talk.getId().equals(Long.parseLong(value))) {
				return talk;
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		return String.valueOf(((Talk)value).getId());
	}
}
