package com.tux.ticket.repository;

import com.tux.ticket.entity.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public interface TicketRepository extends MongoRepository<Ticket, Integer> {

    Optional<Ticket> findByTicketId(String ticketId);

    @Query("{'createdUser.office.officeName': ?0}")

    List<Ticket> findByCreatedUserOffice(String officeValue);

}
