package com.sanjaygoyaljpr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanjaygoyaljpr.persistence.model.UserConferenceRoom;
import com.sanjaygoyaljpr.persistence.repository.UserConferenceRoomRepository;
import com.sanjaygoyaljpr.service.IUserConferenceRoomService;

@Service
public class UserConferenceRoomServiceImpl implements IUserConferenceRoomService {

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
		return userConferenceRoomRepository.getOne(id);
	}

}
