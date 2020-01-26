package com.api.carros.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.carros.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByLogin(String login);
}
