package com.prodyna.pac.conference.ws.test;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;
import org.jboss.resteasy.util.GenericType;

import com.prodyna.pac.conference.common.model.ConferenceEntity;

public class AbstractRESTServiceTest<T extends ConferenceEntity> {

	private static ApacheHttpClient4Executor executor;
	
	protected static final SimpleDateFormat DF = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	
	private Class<T> entityClass;
	
	private String restContext;
	
	public AbstractRESTServiceTest(Class<T> entityClass, String restContext) {
		this.entityClass = entityClass;
		this.restContext = restContext;
	}
	
   	protected static ClientExecutor getExecutor() {
		if (executor == null) {
			Credentials credentials = new UsernamePasswordCredentials("test",
					"prodyna");
			DefaultHttpClient httpClient = new DefaultHttpClient();
			httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY,
					credentials);
			executor = new ApacheHttpClient4Executor(httpClient);
		}
		return executor;
	}

	public T doTestAdd(URL baseUrl, T entity) throws Exception {

		String href = baseUrl.toString().concat(restContext);
		ClientRequest request = getExecutor().createRequest(href);
		request.accept(MediaType.APPLICATION_JSON);
		request.body(MediaType.APPLICATION_JSON, entity);
		ClientResponse<?> response = request.post();
		try {
			if (response.getStatus() != HttpStatus.SC_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}
			href = response.getLocation().getHref();
		} finally {
			response.releaseConnection();
		}

		return doGetEntity(href);
	}


	public T doGetEntity(String href) throws Exception {
		ClientRequest request = getExecutor().createRequest(href);
		request.accept(MediaType.APPLICATION_JSON);
		ClientResponse<T> response = request.get(entityClass);
		try {
			if (response.getStatus() != HttpStatus.SC_OK) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}
			return response.getEntity();
		} finally {
			response.releaseConnection();
		}
	}

	public T doTestUpdate(URL baseUrl, T entity) throws Exception {

		String href = baseUrl.toString().concat(restContext + entity.getId());
		ClientRequest request = getExecutor().createRequest(href);
		request.accept(MediaType.APPLICATION_JSON);
		request.body(MediaType.APPLICATION_JSON, entity);
		ClientResponse<T> response = request.put(entityClass);
		try {
			if (response.getStatus() != HttpStatus.SC_OK) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}
		} finally {
			response.releaseConnection();
		}
		return doGetEntity(href);

   	}

	public void doTestDelete(URL baseUrl, long id) throws Exception {
   		String href = baseUrl.toString().concat(restContext + id);
		ClientRequest request = getExecutor().createRequest(href);
		request.accept(MediaType.APPLICATION_JSON);
		ClientResponse<?> response = request.get();
		try {
			if (response.getStatus() != HttpStatus.SC_OK) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}
		} finally {
			response.releaseConnection();
		}
   	}

	public List<T> doTestList(URL baseUrl) throws Exception {
   		String href = baseUrl.toString().concat(restContext);
		ClientRequest request = getExecutor().createRequest(href);
		request.accept(MediaType.APPLICATION_JSON);
		ClientResponse<List<T>> response = request.get(new GenericType<List<T>>(){});
		try {
			if (response.getStatus() != HttpStatus.SC_OK) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}
			return response.getEntity();
		} finally {
			response.releaseConnection();
		}
   	}
	
	/**
	 * @return the entityClass
	 */
	public Class<T> getEntityClass() {
		return entityClass;
	}


	/**
	 * @return the restContext
	 */
	public String getRestContext() {
		return restContext;
	}
   	
}
