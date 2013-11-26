package com.prodyna.pac.conference.users.service;

import java.util.List;

import com.prodyna.pac.conference.common.service.BaseConferenceService;
import com.prodyna.pac.conference.users.model.Organization;
import com.prodyna.pac.conference.users.model.User;

public interface UserService extends BaseConferenceService<User> {

	List<User> findByOrganization(Organization organization);

}
