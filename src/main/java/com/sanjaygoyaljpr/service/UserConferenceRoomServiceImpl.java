package com.sanjaygoyaljpr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanjaygoyaljpr.model.UserConferenceRoom;
import com.sanjaygoyaljpr.repository.UserConferenceRoomRepository;

@Service
public class UserConferenceRoomServiceImpl implements UserConferenceRoomService {

	@Autowired
	UserConferenceRoomRepository userConferenceRoomRepository;

	@Override
	public void save(UserConferenceRoom userConferenceRoom) {
		userConferenceRoomRepository.save(userConferenceRoom);

	}

	@Override
	public List<UserConferenceRoom> findByUserId(Long id) {
		return userConferenceRoomRepository.findByUserId(id);
	}

	@Override
	public List<UserConferenceRoom> findAll() {
		return userConferenceRoomRepository.findAll();
	}

	@Override
	public List<UserConferenceRoom> findByConferenceRoomId(Long confRoomId) {
		return userConferenceRoomRepository.findByConferenceRoomId(confRoomId);
	}

	@Override
	public UserConferenceRoom findOne(Long id) {
		return userConferenceRoomRepository.findOne(id);
	}

}
