package com.sanjaygoyaljpr.service;

import java.util.List;

import com.sanjaygoyaljpr.persistence.model.ConferenceRoom;

public interface IConferenceRoomService {

	void save(ConferenceRoom conferenceRoom);

	List<ConferenceRoom> findAll();

	ConferenceRoom findOne(Long conferenceRoomId);

	ConferenceRoom findByConfRoomNameContainingIgnoreCase(String confRoomName);
}
