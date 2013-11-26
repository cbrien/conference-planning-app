package com.prodyna.pac.conference.ws.security;

import java.io.IOException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.security.acl.Group;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;
import org.jboss.resteasy.util.Base64;
import org.slf4j.Logger;

@Provider
@ServerInterceptor
public class BasicAuthenticationInterceptor implements PreProcessInterceptor {
	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_SCHEME = "Basic";
	private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse(
			"ACCESS FORBIDDEN", 403, new Headers<Object>());
	private static final ServerResponse SERVER_ERROR = new ServerResponse(
			"INTERNAL SERVER ERROR", 500, new Headers<Object>());
	

	@Inject
	Logger logger;

	@Override
	public ServerResponse preProcess(HttpRequest request,
			ResourceMethod methodInvoked) {
		Method method = methodInvoked.getMethod();

		// Access allowed for all
		if (method.isAnnotationPresent(PermitAll.class)) {
			return null;
		}
		// Access denied for all
		if (method.isAnnotationPresent(DenyAll.class)) {
			return ACCESS_FORBIDDEN;
		}
		// No access restriction specified
		if (!method.isAnnotationPresent(RolesAllowed.class)) {
			return null;
		}

		// Get request headers
		final HttpHeaders headers = request.getHttpHeaders();

		// Fetch authorization header
		final List<String> authorization = headers
				.getRequestHeader(AUTHORIZATION_PROPERTY);

		// If no authorization information present; block access
		if (authorization == null || authorization.isEmpty()) {
			Headers<Object> respHeaders = new Headers<Object>();
			respHeaders.add("WWW-Authenticate", "Basic realm=\"conference-rest-service\"");
			ServerResponse response = new ServerResponse("Authorization required", 401, respHeaders);
			return response;
		}

		// Get encoded username and password
		final String encodedUserPassword = authorization.get(0).replaceFirst(
				AUTHENTICATION_SCHEME + " ", "");

		// Decode username and password
		String usernameAndPassword;
		try {
			usernameAndPassword = new String(Base64.decode(encodedUserPassword));
		} catch (IOException e) {
			return SERVER_ERROR;
		}

		// Split username and password tokens
		final StringTokenizer tokenizer = new StringTokenizer(
				usernameAndPassword, ":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();


		// Verify user access
		RolesAllowed rolesAnnotation = method
				.getAnnotation(RolesAllowed.class);
		Set<String> rolesSet = new HashSet<String>(
				Arrays.asList(rolesAnnotation.value()));

		// Is user valid?
		if (!isUserAllowed(username, password, rolesSet)) {
			return ACCESS_FORBIDDEN;
		}

		// Return null to continue request processing
		return null;
	}

	private boolean isUserAllowed(final String username, final String password,
			final Set<String> rolesSet) {
		
		try {
			LoginContext loginContext = new LoginContext("ApplicationRealm", new CallbackHandler() {
				@Override
				public void handle(Callback[] callbacks) throws IOException,
						UnsupportedCallbackException {
					for (Callback callback : callbacks) {
						if (callback instanceof NameCallback) {
							((NameCallback) callback).setName(username);
						} else if (callback instanceof PasswordCallback) {
							((PasswordCallback) callback).setPassword(password.toCharArray());
						}
					}
				}
			});

			loginContext.login();
			
			Subject subject = loginContext.getSubject();

			for (String role : rolesSet) {
				for (Principal principal : subject.getPrincipals(Group.class)) {
					if (principal instanceof Group && "roles".equalsIgnoreCase(principal.getName())) {
						Group group = (Group) principal;
						for (Enumeration<? extends Principal> members = group.members(); members.hasMoreElements();) {
							Principal member = members.nextElement(); 
							if (role.equals(member.getName())) {
								return true;
							}
						}
					}
				}
			}
			
		} catch (LoginException e) {
			logger.debug("login denied: " + e.getMessage());
		}

		return false;
	}

}
