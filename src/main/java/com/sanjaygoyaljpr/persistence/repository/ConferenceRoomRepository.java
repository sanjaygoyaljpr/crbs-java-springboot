package com.sanjaygoyaljpr.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanjaygoyaljpr.persistence.model.ConferenceRoom;

@Repository
public interface ConferenceRoomRepository extends JpaRepository<ConferenceRoom, Long> {

	ConferenceRoom findByConfRoomNameContainingIgnoreCase(String confRoomName);

}
