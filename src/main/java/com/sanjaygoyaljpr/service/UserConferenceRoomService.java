package com.sanjaygoyaljpr.service;

import java.util.List;

import com.sanjaygoyaljpr.model.UserConferenceRoom;

public interface UserConferenceRoomService {

	void save(UserConferenceRoom userConferenceRoom);

	List<UserConferenceRoom> findByUserId(Long id);

	List<UserConferenceRoom> findAll();

	List<UserConferenceRoom> findByConferenceRoomId(Long confRoomId);

	UserConferenceRoom findOne(Long id);

}
