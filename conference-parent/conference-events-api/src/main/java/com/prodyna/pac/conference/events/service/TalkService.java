package com.prodyna.pac.conference.events.service;

import java.util.List;

import com.prodyna.pac.conference.common.service.BaseConferenceService;
import com.prodyna.pac.conference.events.model.Conference;
import com.prodyna.pac.conference.events.model.Talk;
import com.prodyna.pac.conference.facility.model.Room;
import com.prodyna.pac.conference.users.model.User;

public interface TalkService extends BaseConferenceService<Talk> {

	List<Talk> findByConference(Conference conference);
	List<Talk> findByRoom(Room room);
	List<Talk> findBySpeaker(User speaker);
	List<User> findSpeakersForTalk(Talk talk);

}
