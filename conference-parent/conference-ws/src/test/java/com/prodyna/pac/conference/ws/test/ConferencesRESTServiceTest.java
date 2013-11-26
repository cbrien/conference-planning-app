package com.prodyna.pac.conference.ws.test;

import java.net.URL;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.DataSeedStrategy;
import org.jboss.arquillian.persistence.SeedDataUsing;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.conference.events.model.Conference;
import com.prodyna.pac.conference.facility.model.Location;

@RunWith(Arquillian.class)
@UsingDataSet({ "datasets/conference-test-data.yml" })
@SeedDataUsing(DataSeedStrategy.CLEAN_INSERT)
@Cleanup(phase=TestExecutionPhase.NONE)
@Transactional(TransactionMode.DISABLED)
public class ConferencesRESTServiceTest extends AbstractRESTServiceTest<Conference>{

	@PersistenceContext
	EntityManager em;
	
	public ConferencesRESTServiceTest() {
		super(Conference.class, "rest/conferences/");
	}

//	@Deployment
//	public static Archive<?> createTestArchive() {
//		
//		return doCreateTestArchive();
//	}
	
    @Test
    @InSequence(1)
    public void setupDatabase() {
    	// Workaround to load database with DBUnit as this can only be done on server side tests    	
    }

   	@Test
    @InSequence(2)
	@RunAsClient
	public void testAddDeleteConference(@ArquillianResource URL baseUrl) throws Exception {

		Location location = new Location();
		location.setId(10001l);

		Conference conference = new Conference();
		conference.setName("testing conference");
		conference.setDescription("the conference description");
		conference.setStartDate(DF.parse("01.02.2013 07:00:00"));
		conference.setEndDate(DF.parse("07.02.2013 20:00:00"));
		conference.setLocation(location);

		Conference savedConference = doTestAdd(baseUrl, conference);

		Assert.assertEquals(conference.getName(), savedConference.getName());
		Assert.assertEquals(conference.getDescription(), savedConference.getDescription());
		Assert.assertEquals(conference.getStartDate(), savedConference.getStartDate());
		Assert.assertEquals(conference.getEndDate(), savedConference.getEndDate());
		Assert.assertEquals(conference.getLocation().getId(), savedConference.getLocation().getId());

		doTestDelete(baseUrl, savedConference.getId());
   	}

   	@Test
    @InSequence(3)
	@RunAsClient
	public void testUpdateConference(@ArquillianResource URL baseUrl) throws Exception {
		Location location = new Location();
		location.setId(10002l);

		Conference conference = new Conference();
		conference.setId(10001l);
		conference.setName("updated conference");
		conference.setDescription("the updated conference description");
		conference.setStartDate(DF.parse("01.03.2013 07:00:00"));
		conference.setEndDate(DF.parse("07.03.2013 20:00:00"));
		conference.setLocation(location);

		Conference savedConference = doTestUpdate(baseUrl, conference);

		Assert.assertEquals(conference.getName(), savedConference.getName());
		Assert.assertEquals(conference.getDescription(), savedConference.getDescription());
		Assert.assertEquals(conference.getStartDate(), savedConference.getStartDate());
		Assert.assertEquals(conference.getEndDate(), savedConference.getEndDate());
		Assert.assertEquals(conference.getLocation().getId(), savedConference.getLocation().getId());
   	}

   	@Test
    @InSequence(4)
	@RunAsClient
	public void testListConference(@ArquillianResource URL baseUrl) throws Exception {
		List<Conference> conferences = doTestList(baseUrl);
		
		Assert.assertTrue(conferences.size() >= 2);
   	}

}
