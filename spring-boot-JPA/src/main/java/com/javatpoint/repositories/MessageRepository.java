package com.javatpoint.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javatpoint.models.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{

}