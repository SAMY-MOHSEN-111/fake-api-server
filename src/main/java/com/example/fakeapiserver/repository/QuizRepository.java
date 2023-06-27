package com.example.fakeapiserver.repository;

import com.example.fakeapiserver.entity.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends MongoRepository<Question, String> {
}
