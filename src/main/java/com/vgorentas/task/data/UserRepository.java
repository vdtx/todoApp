package com.vgorentas.task.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vgorentas.task.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	@Query(value = "select * from user where user_name = ?1", nativeQuery = true)
	public Optional<User> getusers(String name);
}
