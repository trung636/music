package com.example.musicmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.musicmanager.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
		User findByUsername( String username);
}
