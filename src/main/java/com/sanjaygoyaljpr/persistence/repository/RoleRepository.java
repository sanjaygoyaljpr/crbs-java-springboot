package com.sanjaygoyaljpr.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanjaygoyaljpr.persistence.model.Role;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByName(String name);

	@Override
	void delete(Role role);

}
