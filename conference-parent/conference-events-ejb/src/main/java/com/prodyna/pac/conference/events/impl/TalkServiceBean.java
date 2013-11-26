package com.prodyna.pac.conference.events.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.prodyna.pac.conference.common.access.Logged;
import com.prodyna.pac.conference.common.audit.Audited;
import com.prodyna.pac.conference.common.exceptions.ConferenceServiceException;
import com.prodyna.pac.conference.common.impl.AbstractBaseConferenceServiceBean;
import com.prodyna.pac.conference.common.monitor.Monitored;
import com.prodyna.pac.conference.events.exceptions.NotDuringConferenceException;
import com.prodyna.pac.conference.events.exceptions.RoomOccupiedException;
import com.prodyna.pac.conference.events.exceptions.SpeakerOccupiedException;
import com.prodyna.pac.conference.events.messaging.TalkChanged;
import com.prodyna.pac.conference.events.messaging.TalkDeleted;
import com.prodyna.pac.conference.events.model.Conference;
import com.prodyna.pac.conference.events.model.Talk;
import com.prodyna.pac.conference.events.service.ConferenceService;
import com.prodyna.pac.conference.events.service.TalkService;
import com.prodyna.pac.conference.facility.model.Room;
import com.prodyna.pac.conference.users.model.User;

@Stateless
@Monitored
@Logged
public class TalkServiceBean extends AbstractBaseConferenceServiceBean<Talk>
		implements TalkService {

	@Inject
	Logger logger;

	@Inject
	EntityManager em;

	@Inject
	AssignmentServiceBean assignmentService;

	@Inject
	ConferenceService conferenceService;

	@Inject
	@TalkChanged
	Event<Talk> talkChangedEvent;

	@Inject
	@TalkDeleted
	Event<Talk> talkDeletedEvent;

	public TalkServiceBean() {
		super(Talk.class);
	}

	@Override
	protected EntityManager getEm() {
		return em;
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Audited
	@Override
	public Long add(Talk talk) throws ConferenceServiceException {
		isValid(talk);
		Long talkId = super.add(talk);
		saveTalkSpeakers(talk);
		return talkId;
	}

	@Audited
	@Override
	public void update(Talk talk) throws ConferenceServiceException {
		isValid(talk);
		super.update(talk);
		deleteTalkSpeakers(talk);
		saveTalkSpeakers(talk);
		talkChangedEvent.fire(talk);
	}

	@Override
	public void delete(long id) {
		Talk talk = get(id);
		deleteTalkSpeakers(talk);
		super.delete(id);
		talkDeletedEvent.fire(talk);
	}
	
	@Override
	public Talk get(long id) {
		Talk talk = super.get(id);
		talk.getSpeakers().addAll(findSpeakersForTalk(talk));
		return talk;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Talk> findByRoom(Room room) {
		Query query = em.createNamedQuery(Talk.FIND_TALKS_BY_ROOM);
		query.setParameter("room", room);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Talk> findBySpeaker(User speaker) {
		Query query = em.createNamedQuery(Talk.FIND_TALKS_BY_SPEAKER);
		query.setParameter("speaker", speaker);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findSpeakersForTalk(Talk talk) {
		Query query = em.createNamedQuery(Talk.FIND_SPEAKERS_FOR_TALK);
		query.setParameter("talk", talk);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Talk> findByConference(Conference conference) {
		Query query = em.createNamedQuery(Talk.FIND_TALKS_BY_CONFERENCE);
		query.setParameter("conference", conference);
		return query.getResultList();
	}

	private void saveTalkSpeakers(Talk talk) throws ConferenceServiceException {
		if (talk.getSpeakers() != null) {
			for (User speaker : talk.getSpeakers()) {
				Assignment assignment = new Assignment();
				assignment.setTalk(talk);
				assignment.setSpeaker(speaker);
				assignmentService.add(assignment);
			}
		}
	}

	private void deleteTalkSpeakers(Talk talk) {
		List<Assignment> assignments = assignmentService.findByTalk(talk);
		for (Assignment assignment : assignments) {
			assignmentService.delete(assignment.getId());
		}
	}

	private void isValid(Talk talk) {
		isDuringConference(talk);
		isRoomAvailable(talk);
		areSpeakersAvailable(talk);
	}

	private void isDuringConference(Talk talk) {
		Long conferenceId = talk.getConference().getId();
		Conference conference = conferenceService.get(conferenceId);
		if (!isInside(talk, conference)) {
			throw new NotDuringConferenceException(conference);
		}
	}

	private void isRoomAvailable(Talk talk) {
		List<Talk> scheduledTalks = findByRoom(talk.getRoom());
		for (Talk scheduledTalk : scheduledTalks) {
			if (scheduledTalk.getId() != talk.getId()
					&& isOverlapping(scheduledTalk, talk)) {
				throw new RoomOccupiedException(scheduledTalk);
			}
		}
	}

	private void areSpeakersAvailable(Talk talk) {
		if (talk.getSpeakers() == null || talk.getSpeakers().isEmpty()) {
			return;
		}

		for (User speaker : talk.getSpeakers()) {
			List<Talk> scheduledTalks = findBySpeaker(speaker);
			for (Talk scheduledTalk : scheduledTalks) {
				if (scheduledTalk.getId() != talk.getId()
						&& isOverlapping(scheduledTalk, talk)) {
					throw new SpeakerOccupiedException(scheduledTalk, speaker);
				}
			}
		}
	}

	private boolean isOverlapping(Talk talk, Talk talk2) {
		long start = talk.getStartDate().getTime();
		long end = talk.getStartDate().getTime()
				+ (60 * 1000 * talk.getLength());
		long start2 = talk2.getStartDate().getTime();
		long end2 = talk2.getStartDate().getTime()
				+ (60 * 1000 * talk2.getLength());
		return ((start < end2) && (end > start2));
	}

	private boolean isInside(Talk talk, Conference conference) {
		long start = talk.getStartDate().getTime();
		long end = talk.getStartDate().getTime()
				+ (60 * 1000 * talk.getLength());
		long start2 = conference.getStartDate().getTime();
		long end2 = conference.getEndDate().getTime();
		return ((start >= start2) && (end <= end2));
	}
}
