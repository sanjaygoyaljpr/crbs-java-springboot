package com.sanjaygoyaljpr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanjaygoyaljpr.persistence.model.ConferenceRoom;
import com.sanjaygoyaljpr.persistence.repository.ConferenceRoomRepository;
import com.sanjaygoyaljpr.service.IConferenceRoomService;

@Service
public class ConferenceRoomServiceImpl implements IConferenceRoomService {

	@Autowired
	ConferenceRoomRepository conferenceRoomRepository;

	@Override
	public void save(ConferenceRoom conferenceRoom) {
		conferenceRoomRepository.save(conferenceRoom);
	}

	@Override
	public List<ConferenceRoom> findAll() {
		return conferenceRoomRepository.findAll();
	}

	@Override
	public ConferenceRoom findOne(Long conferenceRoomId) {
		return conferenceRoomRepository.getOne(conferenceRoomId);
	}

	@Override
	public ConferenceRoom findByConfRoomNameContainingIgnoreCase(String confRoomName) {
		return conferenceRoomRepository.findByConfRoomNameContainingIgnoreCase(confRoomName);
	}
}
