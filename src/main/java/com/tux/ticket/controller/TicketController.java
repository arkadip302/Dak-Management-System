package com.tux.ticket.controller;

import com.tux.ticket.dto.CreateTicketDTO;
import com.tux.ticket.entity.Ticket;
import com.tux.ticket.service.TicketService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ticket/v1")
public class TicketController {

    @Autowired
    TicketService ticketService;


    @PostMapping("/create")
    public ResponseEntity<String> createTicket(@RequestBody CreateTicketDTO createTicketDTO, HttpServletRequest request){
        return ResponseEntity.ok(ticketService.createTicket(createTicketDTO,request.getHeader("Authorization").substring(7)));
    }

    @GetMapping("/fetch")
    public ResponseEntity<Ticket> fetchTicket(@RequestParam String ticketId, HttpServletRequest request){
        return ResponseEntity.ok(ticketService.fetchTicket(ticketId,request.getHeader("Authorization").substring(7)));
    }


}

