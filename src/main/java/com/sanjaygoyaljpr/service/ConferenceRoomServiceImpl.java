package com.sanjaygoyaljpr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanjaygoyaljpr.model.ConferenceRoom;
import com.sanjaygoyaljpr.repository.ConferenceRoomRepository;

@Service
public class ConferenceRoomServiceImpl implements ConferenceRoomService {

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
