package com.tux.ticket.repository;

import com.tux.ticket.entity.Office;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OfficeRepository extends MongoRepository<Office,Integer> {


}
