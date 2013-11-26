package com.prodyna.pac.conference.ws.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.DataSeedStrategy;
import org.jboss.arquillian.persistence.SeedDataUsing;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
@UsingDataSet({ "datasets/conference-test-data.yml" })
@SeedDataUsing(DataSeedStrategy.CLEAN_INSERT)
@Cleanup(phase=TestExecutionPhase.NONE)
@Transactional(TransactionMode.DISABLED)
public class RESTServiceDeployment {
	
	@PersistenceContext
	EntityManager em;

	@Deployment
	public static Archive<?> createTestArchive() {
		WebArchive archive = ShrinkWrap.create(WebArchive.class, "test.war");
		List<File> libs = new ArrayList<File>();
		libs.addAll(Arrays.asList(Maven
				.resolver()
				.loadPomFromFile("pom.xml")
				.resolve(
						Arrays.asList(
								"com.prodyna.pac.conference:conference-facility-api",
								"com.prodyna.pac.conference:conference-facility-ejb",
								"com.prodyna.pac.conference:conference-users-api",
								"com.prodyna.pac.conference:conference-users-ejb",
								"com.prodyna.pac.conference:conference-events-api",
								"com.prodyna.pac.conference:conference-events-ejb",
								"com.prodyna.pac.conference:conference-common-api",
								"com.prodyna.pac.conference:conference-common-ejb"))
				.withoutTransitivity().asFile()));

		archive.addAsLibraries(libs.toArray(new File[0]));

		archive.addPackages(true, "com.prodyna.pac.conference.ws")
				.addAsResource("META-INF/test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource("META-INF/beans.xml", "beans.xml")
				.addAsWebInfResource("META-INF/jboss-deployment-structure.xml",
						"jboss-deployment-structure.xml")
				.addAsWebInfResource("test-ds.xml", "test-ds.xml")
				.addAsManifestResource("META-INF/MANIFEST.MF", "MANIFEST.MF");

		return archive;
	}

	@Test
	public void setupDatabase() {
		// have to create server test case to include DBUnit persistence properties in deployment
	}
	
}
