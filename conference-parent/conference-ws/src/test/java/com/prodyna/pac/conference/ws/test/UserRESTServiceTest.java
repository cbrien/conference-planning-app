package com.prodyna.pac.conference.ws.test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import com.prodyna.pac.conference.events.service.ConferenceService;
import com.prodyna.pac.conference.events.service.TalkService;
import com.prodyna.pac.conference.facility.service.LocationService;
import com.prodyna.pac.conference.facility.service.RoomService;
import com.prodyna.pac.conference.users.model.Organization;
import com.prodyna.pac.conference.users.model.User;
import com.prodyna.pac.conference.users.service.OrganizationService;
import com.prodyna.pac.conference.users.service.UserService;

@RunWith(Arquillian.class)
public class UserRESTServiceTest {
	
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
						"com.prodyna.pac.conference:conference-events-ejb",
						"com.prodyna.pac.conference:conference-common-api",
						"com.prodyna.pac.conference:conference-common-ejb"
						)).withoutTransitivity().asFile()));
		
		archive.addAsLibraries(libs.toArray(new File[0]));

		return archive
				.addPackages(true, "com.prodyna.pac.conference.ws")
				.addAsResource("META-INF/test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource("META-INF/beans.xml", "beans.xml")
				// Deploy our test datasource
				.addAsWebInfResource("test-ds.xml", "test-ds.xml");
	}
	
	@Test
	public void testAdd() throws Exception {
		
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

		ClientRequest request = new ClientRequest(
				"http://localhost:8080/test/rest/users/" + speaker.getId());
		request.accept("application/json");
		ClientResponse<User> response = request.get(User.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}

		User savedUser = response.getEntity();
		Assert.assertEquals(speaker, savedUser);
	}
	
}
