package com.prodyna.pac.conference.facility.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

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

import com.prodyna.pac.conference.facility.impl.LocationServiceBean;
import com.prodyna.pac.conference.facility.model.Location;
import com.prodyna.pac.conference.facility.service.LocationService;

@RunWith(Arquillian.class)
public class LocationServiceTest {
	
	@Inject
	LocationService locationService;
	
	@Inject
	Logger logger;

	@Deployment
	public static Archive<?> createTestArchive() {
		WebArchive archive = ShrinkWrap.create(WebArchive.class, "test.war");
		List<File> libs = new ArrayList<File>();
				 
		
		libs.addAll(Arrays.asList(Maven.resolver()
				.loadPomFromFile("pom.xml").resolve("com.prodyna.pac.conference:conference-facility-api")
				.withTransitivity().asFile()));
		libs.addAll(Arrays.asList(Maven.resolver()
				.loadPomFromFile("pom.xml").resolve("com.prodyna.pac.conference:conference-common")
				.withTransitivity().asFile()));
		
		archive.addAsLibraries(libs.toArray(new File[0]));

		return archive
				.addClasses(LocationServiceBean.class)
				.addAsResource("META-INF/test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				// Deploy our test datasource
				.addAsWebInfResource("test-ds.xml", "test-ds.xml");
	}
	
	@Test
	public void testAddLocation(){
		Location location = new Location();
		location.setAddress("here comes the address");
		location.setName("Test Location Name");
		location.setDescription("here comes the desciption");
		locationService.addLocation(location);
		
		logger.info("new Location created: " + location.getId());
	}

}
