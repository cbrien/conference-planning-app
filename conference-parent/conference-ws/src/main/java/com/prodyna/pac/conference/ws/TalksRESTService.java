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
package com.prodyna.pac.conference.ws;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;

import com.prodyna.pac.conference.events.model.Conference;
import com.prodyna.pac.conference.events.model.Talk;
import com.prodyna.pac.conference.events.service.TalkService;
import com.prodyna.pac.conference.facility.model.Room;
import com.prodyna.pac.conference.users.model.User;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the talks table.
 */
@Path("talks")
public class TalksRESTService {

    @Inject
    private Logger log;

    @Inject
    private Validator validator;

    @Inject
    private TalkService talkService;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public List<Talk> listTalks() {
        return talkService.list();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Talk getTalk(@PathParam("id") long id) {
    	Talk talk = talkService.get(id);
        if (talk == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return talk;
    }

    @GET
    @Path("/{id:[0-9][0-9]*}/speakers/")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public List<User> getSpeakersByTalk(@PathParam("id") long id) {
    	Talk talk = talkService.get(id);
        if (talk == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return talkService.findSpeakersForTalk(talk);
    }
    
    @PUT
    @Path("/{id:[0-9][0-9]*}/speakers/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response addSpeakerToTalk(@PathParam("id") long id, User user) {
    	Talk talk = talkService.get(id);
        if (talk == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        talk.getSpeakers().add(user);
        ResponseBuilder builder = null;
		try {
        	validateTalk(talk);
			talkService.update(talk);
            builder  = Response.ok();
        } catch (ConstraintViolationException ce) {
            // Handle bean validation issues
            builder = createViolationResponse(ce.getConstraintViolations());
        } catch (ValidationException e) {
            // Handle the unique constrain violation
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("email", "Email taken");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        return builder.build();
    }
    
    @GET
    @Path("/speaker/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public List<Talk> getTalksBySpeaker(@PathParam("id") long id) {
    	User user = new User();
    	user.setId(id);
    	return talkService.findBySpeaker(user);
    }

    @GET
    @Path("/room/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public List<Talk> getTalksByRoom(@PathParam("id") long id) {
    	Room room = new Room();
    	room.setId(id);
    	return talkService.findByRoom(room);
    }

    @GET
    @Path("/conference/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public List<Talk> getTalksByConference(@PathParam("id") long id) {
    	Conference conference = new Conference();
    	conference.setId(id);
    	return talkService.findByConference(conference);
    }
    
    
    @PUT
    @Path("/{id:[0-9][0-9]*}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"conference-admin"})
    public Response putTalk(@PathParam("id") long id, Talk talk, @Context UriInfo ui) {
    	talk.setId(id);
    	Response.ResponseBuilder builder = null;

        try {
            // Validates talk using bean validation
            validateTalk(talk);
            talkService.update(talk);
            // Create an "ok" response
            builder = Response.ok();
        } catch (ConstraintViolationException ce) {
            // Handle bean validation issues
            builder = createViolationResponse(ce.getConstraintViolations());
        } catch (ValidationException e) {
            // Handle the unique constrain violation
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("email", "Email taken");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }
    
    /**
     * deletes the talk with the given id
     * @param id
     * @return ok or not found
     */
    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"conference-admin"})
    public Response deleteTalk(@PathParam("id") long id) {

        Talk talk = talkService.get(id);
    	if (talk == null) {
    		return Response.status(Status.NOT_FOUND).build();
    	}
    	
    	Response.ResponseBuilder builder = null;
        try {
            talkService.delete(id);
            // Create an "ok" response
            builder = Response.ok();
        } catch (ConstraintViolationException ce) {
            // Handle bean validation issues
            builder = createViolationResponse(ce.getConstraintViolations());
        }  catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }
    
    /**
     * Creates a new talk from the values provided. Performs validation, and will return a JAX-RS response with either 200 ok,
     * or with a map of fields, and related errors.
     */
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"conference-admin"})
    public Response createTalk(Talk talk, @Context UriInfo ui) {

        Response.ResponseBuilder builder = null;

        try {
            // Validates talk using bean validation
            validateTalk(talk);
            talkService.add(talk);
            URI uri = ui.getAbsolutePathBuilder().path(String.valueOf(talk.getId())).build(new Object[0]);
            // Create an "ok" response
            builder = Response.created(uri);
        } catch (ConstraintViolationException ce) {
            // Handle bean validation issues
            builder = createViolationResponse(ce.getConstraintViolations());
        } catch (ValidationException e) {
            // Handle the unique constrain violation
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("email", "Email taken");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }

    /**
     * <p>
     * Validates the given Talk variable and throws validation exceptions based on the type of error. If the error is standard
     * bean validation errors then it will throw a ConstraintValidationException with the set of the constraints violated.
     * </p>
     * <p>
     * If the error is caused because an existing talk with the same email is registered it throws a regular validation
     * exception so that it can be interpreted separately.
     * </p>
     * 
     * @param talk Talk to be validated
     * @throws ConstraintViolationException If Bean Validation errors exist
     * @throws ValidationException If talk with the same email already exists
     */
    private void validateTalk(Talk talk) throws ConstraintViolationException, ValidationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<Talk>> violations = validator.validate(talk);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }

    }

    /**
     * Creates a JAX-RS "Bad Request" response including a map of all violation fields, and their message. This can then be used
     * by clients to show violations.
     * 
     * @param violations A set of violations that needs to be reported
     * @return JAX-RS response containing all violations
     */
    private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
        log.debug("Validation completed. violations found: " + violations.size());

        Map<String, String> responseObj = new HashMap<String, String>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }

}
