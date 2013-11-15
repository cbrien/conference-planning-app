package com.prodyna.pac.conference.common.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.slf4j.Logger;

import com.prodyna.pac.conference.common.access.Logged;
import com.prodyna.pac.conference.common.audit.Audited;
import com.prodyna.pac.conference.common.exceptions.ConferenceServiceException;
import com.prodyna.pac.conference.common.model.ConferenceEntity;
import com.prodyna.pac.conference.common.service.BaseConferenceService;

public abstract class AbstractBaseConferenceServiceBean<T extends ConferenceEntity>
		implements BaseConferenceService<T> {

	private final Class<T> entityClass;

	public AbstractBaseConferenceServiceBean(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	abstract protected EntityManager getEm();

	abstract protected Logger getLogger();

	@Override
	public T get(long id) {
		return getEm().find(entityClass, id);
	}

	@Audited("id")
	@Override
	public Long add(T object) throws ConferenceServiceException {
		if (object != null && object.getId() != null
				&& get(object.getId()) != null) {
			throw new ConferenceServiceException(entityClass.getSimpleName()
					+ " allready exists");
		}
		try {
			getEm().persist(object);
			getEm().flush();
			getLogger().info(
					entityClass.getSimpleName() + " added with id: "
							+ object.getId());
			return object.getId();
		} catch (PersistenceException pe) {
			throw new ConferenceServiceException(pe.getMessage());
		}
	}

	@Audited("id")
	@Override
	public void update(T object) throws ConferenceServiceException {
		if (object == null || get(object.getId()) == null) {
			throw new ConferenceServiceException(entityClass.getSimpleName()
					+ " not found");
		}
		;
		try {
			getEm().merge(object);
			getEm().flush();
			getLogger().info(
					entityClass.getSimpleName() + " updated with id: "
							+ object.getId());
		} catch (PersistenceException pe) {
			throw new ConferenceServiceException(pe.getMessage());
		}
	}

	@Override
	public void delete(long id) {
		T object = get(id);
		if (object != null) {
			getEm().remove(object);
			getLogger().info(
					entityClass.getSimpleName() + " deleted with id: "
							+ object.getId());
		}
	}

	@Override
	public List<T> list() {
		CriteriaQuery<T> cq = getEm().getCriteriaBuilder().createQuery(
				entityClass);
		TypedQuery<T> query = getEm().createQuery(
				cq.select(cq.from(entityClass)));
		return query.getResultList();
	}

}
