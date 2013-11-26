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
import com.prodyna.pac.conference.events.model.Talk;
import com.prodyna.pac.conference.facility.model.Room;
import com.prodyna.pac.conference.users.model.User;

@RunWith(Arquillian.class)
@UsingDataSet({ "datasets/conference-test-data.yml" })
@SeedDataUsing(DataSeedStrategy.CLEAN_INSERT)
@Cleanup(phase=TestExecutionPhase.NONE)
@Transactional(TransactionMode.DISABLED)
public class TalksRESTServiceTest extends AbstractRESTServiceTest<Talk>{

	@PersistenceContext
	EntityManager em;
	
	public TalksRESTServiceTest() {
		super(Talk.class, "rest/talks/");
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
	public void testAddDeleteTalk(@ArquillianResource URL baseUrl) throws Exception {

		Conference conference = new Conference();
		conference.setId(10001l);
		User speaker1 = new User();
		speaker1.setId(10001l);
		User speaker2 = new User();
		speaker2.setId(10002l);
		Room room = new Room();
		room.setId(10001l);
		
		Talk talk = new Talk();
		talk.setName("testing talk");
		talk.setDescription("the talk description");
		talk.setStartDate(DF.parse("02.10.2012 08:00:00"));
		talk.setLength(60);
		talk.setConference(conference);
		talk.setRoom(room);
		talk.getSpeakers().add(speaker1);
		talk.getSpeakers().add(speaker2);

		Talk savedTalk = doTestAdd(baseUrl, talk);

		Assert.assertEquals(talk.getName(), savedTalk.getName());
		Assert.assertEquals(talk.getDescription(), savedTalk.getDescription());
		Assert.assertEquals(talk.getStartDate(), savedTalk.getStartDate());
		Assert.assertEquals(talk.getLength(), savedTalk.getLength());
		Assert.assertEquals(talk.getConference().getId(), savedTalk.getConference().getId());
		Assert.assertEquals(talk.getRoom().getId(), savedTalk.getRoom().getId());
		Assert.assertEquals(savedTalk.getSpeakers().size(), 2);

		doTestDelete(baseUrl, savedTalk.getId());
   	}

   	@Test
    @InSequence(3)
	@RunAsClient
	public void testUpdateTalk(@ArquillianResource URL baseUrl) throws Exception {

		User speaker = new User();
		speaker.setId(10002l);

		String href = baseUrl.toString().concat(getRestContext() + "10002");

		Talk talk = doGetEntity(href);
		talk.setId(10001l);
		talk.setName("updated talk");
		talk.setDescription("the updated talk description");
		talk.setStartDate(DF.parse("03.10.2012 12:00:00"));
		talk.setLength(120);
		talk.getSpeakers().clear();
		talk.getSpeakers().add(speaker);
		
		Talk savedTalk = doTestUpdate(baseUrl, talk);

		Assert.assertEquals(talk.getName(), savedTalk.getName());
		Assert.assertEquals(talk.getDescription(), savedTalk.getDescription());
		Assert.assertEquals(talk.getStartDate(), savedTalk.getStartDate());
		Assert.assertEquals(talk.getLength(), savedTalk.getLength());
		Assert.assertEquals(talk.getConference().getId(), savedTalk.getConference().getId());
		Assert.assertEquals(talk.getRoom().getId(), savedTalk.getRoom().getId());
		Assert.assertEquals(savedTalk.getSpeakers().size(), 1);
		Assert.assertEquals(savedTalk.getSpeakers().iterator().next().getId(), speaker.getId());
   	}

   	@Test
    @InSequence(4)
	@RunAsClient
	public void testListTalk(@ArquillianResource URL baseUrl) throws Exception {
		List<Talk> talks = doTestList(baseUrl);
		
		Assert.assertTrue(talks.size() >= 2);
   	}

}
