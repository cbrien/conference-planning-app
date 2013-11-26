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

import com.prodyna.pac.conference.users.model.Organization;

@RunWith(Arquillian.class)
@UsingDataSet({ "datasets/conference-test-data.yml" })
@SeedDataUsing(DataSeedStrategy.CLEAN_INSERT)
@Cleanup(phase=TestExecutionPhase.NONE)
@Transactional(TransactionMode.DISABLED)
public class OrganizationsRESTServiceTest extends AbstractRESTServiceTest<Organization>{

	@PersistenceContext
	EntityManager em;
	
	public OrganizationsRESTServiceTest() {
		super(Organization.class, "rest/organizations/");
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
	public void testAddDeleteOrganization(@ArquillianResource URL baseUrl) throws Exception {

		Organization organization = new Organization();
		organization.setName("test organization");
		organization.setDescription("this is a test organization");

		Organization savedOrganization = doTestAdd(baseUrl, organization);

		Assert.assertEquals(organization.getName(), savedOrganization.getName());
		Assert.assertEquals(organization.getDescription(), savedOrganization.getDescription());

		doTestDelete(baseUrl, savedOrganization.getId());
   	}

   	@Test
    @InSequence(3)
	@RunAsClient
	public void testUpdateOrganization(@ArquillianResource URL baseUrl) throws Exception {

   		Organization organization = new Organization();
   		organization.setId(10001l);
		organization.setName("updated organization");
		organization.setDescription("this is a updated organization");

		Organization savedOrganization = doTestUpdate(baseUrl, organization);

		Assert.assertEquals(organization.getName(), savedOrganization.getName());
		Assert.assertEquals(organization.getDescription(), savedOrganization.getDescription());
   	}

   	@Test
    @InSequence(4)
	@RunAsClient
	public void testListOrganization(@ArquillianResource URL baseUrl) throws Exception {
		List<Organization> organizations = doTestList(baseUrl);
		
		Assert.assertTrue(organizations.size() >= 2);
   	}

}
