package com.sanjaygoyaljpr.service;

import java.util.List;

import com.sanjaygoyaljpr.model.ConferenceRoom;

public interface ConferenceRoomService {

	void save(ConferenceRoom conferenceRoom);

	List<ConferenceRoom> findAll();

	ConferenceRoom findOne(Long conferenceRoomId);

	ConferenceRoom findByConfRoomNameContainingIgnoreCase(String confRoomName);
}
