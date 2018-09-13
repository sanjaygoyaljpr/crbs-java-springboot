package com.sanjaygoyaljpr.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanjaygoyaljpr.persistence.model.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	@Override
	void delete(User user);

}
