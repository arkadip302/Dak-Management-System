package com.tux.ticket.repository;

import com.tux.ticket.entity.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public interface TicketRepository extends MongoRepository<Ticket, Integer> {

    Optional<Ticket> findById(String ticketId);
}
