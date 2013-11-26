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

import com.prodyna.pac.conference.facility.model.Location;
import com.prodyna.pac.conference.facility.model.Room;

@RunWith(Arquillian.class)
@UsingDataSet({ "datasets/conference-test-data.yml" })
@SeedDataUsing(DataSeedStrategy.CLEAN_INSERT)
@Cleanup(phase=TestExecutionPhase.NONE)
@Transactional(TransactionMode.DISABLED)
public class RoomsRESTServiceTest extends AbstractRESTServiceTest<Room>{

	@PersistenceContext
	EntityManager em;
	
	public RoomsRESTServiceTest() {
		super(Room.class, "rest/rooms/");
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
	public void testAddDeleteRoom(@ArquillianResource URL baseUrl) throws Exception {

		Location location = new Location();
		location.setId(10001l);

		Room room = new Room();
		room.setName("testing room");
		room.setDescription("the room description");
		room.setCapacity(10);
		room.setLocation(location);

		Room savedRoom = doTestAdd(baseUrl, room);

		Assert.assertEquals(room.getName(), savedRoom.getName());
		Assert.assertEquals(room.getDescription(), savedRoom.getDescription());
		Assert.assertEquals(room.getCapacity(), savedRoom.getCapacity());
		Assert.assertEquals(room.getLocation().getId(), savedRoom.getLocation().getId());

		doTestDelete(baseUrl, savedRoom.getId());
   	}

   	@Test
    @InSequence(3)
	@RunAsClient
	public void testUpdateRoom(@ArquillianResource URL baseUrl) throws Exception {
		Location location = new Location();
		location.setId(10002l);

		Room room = new Room();
		room.setId(10001l);
		room.setName("updated room");
		room.setDescription("the updated room description");
		room.setCapacity(20);
		room.setLocation(location);

		Room savedRoom = doTestUpdate(baseUrl, room);

		Assert.assertEquals(room.getName(), savedRoom.getName());
		Assert.assertEquals(room.getDescription(), savedRoom.getDescription());
		Assert.assertEquals(room.getCapacity(), savedRoom.getCapacity());
		Assert.assertEquals(room.getLocation().getId(), savedRoom.getLocation().getId());
   	}

   	@Test
    @InSequence(4)
	@RunAsClient
	public void testListRoom(@ArquillianResource URL baseUrl) throws Exception {
		List<Room> rooms = doTestList(baseUrl);
		
		Assert.assertTrue(rooms.size() >= 2);
   	}

}
