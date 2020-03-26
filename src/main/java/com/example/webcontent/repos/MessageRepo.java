package com.example.webcontent.repos;

import com.example.webcontent.domain.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepo extends CrudRepository<Message, Long> {
}
