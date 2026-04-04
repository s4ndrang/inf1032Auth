package com.example.inf1032Auth.repository;

import com.example.inf1032Auth.model.Auth;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthRepository extends MongoRepository<Auth, UUID> {
    @Query("{'username': ?0 }")
    Auth findByUsername(String username);
}

//Méthodes déjà fournis par CrudRepository
//save()/saveAll()
//findById()
//existsById() TODO
//findAll()
//count()
//delete()
//deleteAll()