package com.sanjaygoyaljpr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanjaygoyaljpr.model.UserConferenceRoom;

@Repository
public interface UserConferenceRoomRepository extends JpaRepository<UserConferenceRoom, Long> {

	List<UserConferenceRoom> findByUserId(Long id);

	List<UserConferenceRoom> findByConferenceRoomId(Long confRoomId);

}
