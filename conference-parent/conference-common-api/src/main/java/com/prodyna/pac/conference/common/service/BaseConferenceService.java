package com.prodyna.pac.conference.common.service;

import java.util.List;

import com.prodyna.pac.conference.common.exceptions.ConferenceServiceException;
import com.prodyna.pac.conference.common.model.ConferenceEntity;

public interface BaseConferenceService<T extends ConferenceEntity> {
	T get(long id);

	Long add(T object) throws ConferenceServiceException;

	void update(T object) throws ConferenceServiceException;

	void delete(long id);

	List<T> list();
}
