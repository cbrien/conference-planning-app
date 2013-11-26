package com.prodyna.pac.conference.ws.test;

import java.net.URL;
import java.text.SimpleDateFormat;
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

import com.prodyna.pac.conference.users.model.Organization;
import com.prodyna.pac.conference.users.model.User;

@RunWith(Arquillian.class)
@UsingDataSet({ "datasets/conference-test-data.yml" })
@SeedDataUsing(DataSeedStrategy.CLEAN_INSERT)
@Cleanup(phase=TestExecutionPhase.NONE)
@Transactional(TransactionMode.DISABLED)
public class UsersRESTServiceTest extends AbstractRESTServiceTest<User>{

	@PersistenceContext
	EntityManager em;
	
	public UsersRESTServiceTest() {
		super(User.class, "rest/users/");
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
	public void testAddDeleteUser(@ArquillianResource URL baseUrl) throws Exception {

		Organization organization = new Organization();
		organization.setId(10001l);

		User user = new User();
		user.setEmail("email@testing.local");
		user.setFirstname("firstname");
		user.setLastname("lastname");
		user.setDescription("the user description");
		user.setAddress("address");
		user.setDateOfBirth(SimpleDateFormat.getDateInstance().parse(
				"01.10.1990"));
		user.setOrganization(organization);

		User savedUser = doTestAdd(baseUrl, user);

		Assert.assertEquals(user.getAddress(), savedUser.getAddress());
		Assert.assertEquals(user.getDescription(), savedUser.getDescription());
		Assert.assertEquals(user.getEmail(), savedUser.getEmail());
		Assert.assertEquals(user.getFirstname(), savedUser.getFirstname());
		Assert.assertEquals(user.getLastname(), savedUser.getLastname());
		Assert.assertEquals(user.getDateOfBirth(), savedUser.getDateOfBirth());
		Assert.assertEquals(user.getOrganization().getId(), savedUser.getOrganization().getId());
		
		doTestDelete(baseUrl, savedUser.getId());
	}

   	@Test
    @InSequence(3)
	@RunAsClient
	public void testUpdateUser(@ArquillianResource URL baseUrl) throws Exception {
		Organization organization = new Organization();
		organization.setId(10002l);

		User user = new User();
		user.setId(10001l);
		user.setEmail("updated@testing.local");
		user.setFirstname("updated firstname");
		user.setLastname("updated lastname");
		user.setDescription("the updated user description");
		user.setAddress("updated address");
		user.setDateOfBirth(SimpleDateFormat.getDateInstance().parse(
				"01.10.2000"));
		user.setOrganization(organization);

		User savedUser = doTestUpdate(baseUrl, user);

		Assert.assertEquals(user.getAddress(), savedUser.getAddress());
		Assert.assertEquals(user.getDescription(), savedUser.getDescription());
		Assert.assertEquals(user.getEmail(), savedUser.getEmail());
		Assert.assertEquals(user.getFirstname(), savedUser.getFirstname());
		Assert.assertEquals(user.getLastname(), savedUser.getLastname());
		Assert.assertEquals(user.getDateOfBirth(), savedUser.getDateOfBirth());
		Assert.assertEquals(user.getOrganization().getId(), savedUser.getOrganization().getId());
   	}

   	@Test
    @InSequence(4)
	@RunAsClient
	public void testListUser(@ArquillianResource URL baseUrl) throws Exception {
		List<User> users = doTestList(baseUrl);
		
		Assert.assertTrue(users.size() >= 2);
   	}
	
}
