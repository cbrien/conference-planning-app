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

import com.prodyna.pac.conference.facility.model.Location;
import com.prodyna.pac.conference.facility.service.LocationService;

@Model
public class LocationConverter implements Converter {

    @Inject
    private LocationService locationService;

    private List<Location> locations;

    // @Named provides access the return value via the EL variable name "locations" in the UI (e.g.
    // Facelets or JSP view)
    @Produces
    @Named("locations")
    public List<Location> getLocations() {
        return locations;
    }

    public void onLocationListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Location location) {
        retrieveAllLocationsOrderedByName();
    }

    @PostConstruct
    public void retrieveAllLocationsOrderedByName() {
        locations = locationService.list();
    }

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		for (Location location : locations) {
			if (location.getId().equals(Long.parseLong(value))) {
				return location;
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		return String.valueOf(((Location)value).getId());
	}
}
