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

@RunWith(Arquillian.class)
@UsingDataSet({ "datasets/conference-test-data.yml" })
@SeedDataUsing(DataSeedStrategy.CLEAN_INSERT)
@Cleanup(phase=TestExecutionPhase.NONE)
@Transactional(TransactionMode.DISABLED)
public class LocationsRESTServiceTest extends AbstractRESTServiceTest<Location>{

	@PersistenceContext
	EntityManager em;
	
	public LocationsRESTServiceTest() {
		super(Location.class, "rest/locations/");
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
	public void testAddDeleteLocation(@ArquillianResource URL baseUrl) throws Exception {

		Location location = new Location();
		location.setName("test location");
		location.setDescription("this is a test location");
		location.setAddress("testing address");

		Location savedLocation = doTestAdd(baseUrl, location);

		Assert.assertEquals(location.getName(), savedLocation.getName());
		Assert.assertEquals(location.getDescription(), savedLocation.getDescription());
		Assert.assertEquals(location.getAddress(), savedLocation.getAddress());

		doTestDelete(baseUrl, savedLocation.getId());
   	}

   	@Test
    @InSequence(3)
	@RunAsClient
	public void testUpdateLocation(@ArquillianResource URL baseUrl) throws Exception {

   		Location location = new Location();
   		location.setId(10001l);
		location.setName("updated location");
		location.setDescription("this is a updated location");
		location.setAddress("updated address");

		Location savedLocation = doTestUpdate(baseUrl, location);

		Assert.assertEquals(location.getName(), savedLocation.getName());
		Assert.assertEquals(location.getDescription(), savedLocation.getDescription());
		Assert.assertEquals(location.getAddress(), savedLocation.getAddress());
   	}

   	@Test
    @InSequence(4)
	@RunAsClient
	public void testListLocation(@ArquillianResource URL baseUrl) throws Exception {
		List<Location> locations = doTestList(baseUrl);
		
		Assert.assertTrue(locations.size() >= 2);
   	}

}
