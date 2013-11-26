package com.prodyna.pac.conference.events.test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import com.prodyna.pac.conference.events.exceptions.NotDuringConferenceException;
import com.prodyna.pac.conference.events.model.Conference;
import com.prodyna.pac.conference.events.model.Talk;
import com.prodyna.pac.conference.events.service.ConferenceService;
import com.prodyna.pac.conference.events.service.TalkService;
import com.prodyna.pac.conference.facility.model.Location;
import com.prodyna.pac.conference.facility.model.Room;
import com.prodyna.pac.conference.facility.service.LocationService;
import com.prodyna.pac.conference.facility.service.RoomService;
import com.prodyna.pac.conference.users.model.Organization;
import com.prodyna.pac.conference.users.model.User;
import com.prodyna.pac.conference.users.service.OrganizationService;
import com.prodyna.pac.conference.users.service.UserService;

@RunWith(Arquillian.class)
public class ConferenceServiceTest {
	
	@PersistenceContext
	EntityManager em;
	
	@Inject
	TalkService talkService;
	
	@Inject
	ConferenceService conferenceService;
	
	@Inject
	LocationService locationService;

	@Inject
	RoomService roomService;
	
	@Inject
	OrganizationService organizationService;
	
	@Inject
	UserService userService;

	@Inject
	Logger logger;

	@Deployment
	public static Archive<?> createTestArchive() {
		WebArchive archive = ShrinkWrap.create(WebArchive.class, "test.war");
		List<File> libs = new ArrayList<File>();
		libs.addAll(Arrays.asList(Maven.resolver().loadPomFromFile("pom.xml")
				.resolve(Arrays.asList(
						"com.prodyna.pac.conference:conference-facility-api",
						"com.prodyna.pac.conference:conference-facility-ejb",
						"com.prodyna.pac.conference:conference-users-api",
						"com.prodyna.pac.conference:conference-users-ejb",
						"com.prodyna.pac.conference:conference-events-api",
						"com.prodyna.pac.conference:conference-common-api",
						"com.prodyna.pac.conference:conference-common-ejb"
						)).withoutTransitivity().asFile()));
		
		archive.addAsLibraries(libs.toArray(new File[0]));

		return archive
				.addPackages(true, "com.prodyna.pac.conference.events")
				.addAsResource("META-INF/test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource("META-INF/beans.xml", "beans.xml")
				// Deploy our test datasource
				.addAsWebInfResource("test-ds.xml", "test-ds.xml");
	}
	
	@Test
	public void testAdd() throws Exception {
		Location location = new Location();
		location.setAddress("here comes the address");
		location.setName("testing add");
		location.setDescription("here comes the description");
		locationService.add(location);
		
		Room room = new Room();
		room.setCapacity(10);
		room.setDescription("the small room");
		room.setName("small");
		room.setLocation(location);
		roomService.add(room);
		
		Organization organization = new Organization();
		organization.setName("university");
		organization.setDescription("a big university");
		organizationService.add(organization);
		
		User speaker = new User();
		speaker.setOrganization(organization);
		speaker.setDateOfBirth(SimpleDateFormat.getDateInstance().parse("01.10.1990"));
		speaker.setDescription("some very smart boy");
		speaker.setEmail("karl@universe.all");
		speaker.setFirstname("karl");
		speaker.setLastname("smart");
		speaker.setAddress("galaxy street 1");
		userService.add(speaker);

		Conference conference = new Conference();
		conference.setLocation(location);
		conference.setName("Galaxy One");
		conference.setDescription("Discussions about the universe");
		conference.setStartDate(SimpleDateFormat.getDateInstance().parse("02.01.2014"));
		conference.setEndDate(SimpleDateFormat.getDateInstance().parse("05.01.2014"));
		conferenceService.add(conference);
		
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");

		Talk talk = new Talk();
		talk.setConference(conference);
		talk.setDescription("cleaning up stardust");
		talk.setName("stardust");
		talk.setRoom(room);
		talk.setStartDate(df.parse("01.01.2014 08:00"));
		talk.setLength(60);
		try {
			talkService.add(talk);
			Assert.fail("talk is not during conference");
		} catch (EJBException e) {
			Assert.assertTrue(e.getCause() instanceof NotDuringConferenceException);
		}
		
		talk.setStartDate(df.parse("02.01.2014 08:00"));
		talkService.add(talk);
		
		talk.getSpeakers().add(speaker);
		talkService.update(talk);
	}
	
}
