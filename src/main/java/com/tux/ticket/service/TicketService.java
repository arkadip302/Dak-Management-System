package com.tux.ticket.service;


import com.tux.ticket.dto.CreateTicketDTO;
import com.tux.ticket.entity.Ticket;
import com.tux.ticket.exception.CustomServiceException;
import com.tux.ticket.repository.TicketRepository;
import com.tux.ticket.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TicketService {
    private static ModelMapper modelMapper = new ModelMapper();

    @Autowired
    UserRepository userRepository;
    @Autowired
    TicketRepository ticketRepository;

    private String iamAppUrl = "http://app:8081/iam/v1/auth";

    @Autowired
    private final RestTemplate restTemplate;

    public TicketService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public String createTicket(CreateTicketDTO createTicketDTO,String token) {

        if(callRemoteApi(token) && createTicketDTO.getAssignedUser() != null || createTicketDTO.getCreatedUser() != null){
            Ticket ticket = modelMapper.map(createTicketDTO, Ticket.class);
            ticket.setCreatedUser(userRepository.findByName(createTicketDTO.getCreatedUser()).orElseThrow(() ->
                    new CustomServiceException("Not Able To Find Create User")));
            ticket.setAssignedUser(userRepository.findByName(createTicketDTO.getAssignedUser()).orElseThrow(() ->
                    new CustomServiceException("Not Able To Find Assigned User")));
            ticketRepository.save(ticket);
            return "Ticket Created !!!";
        }
        return "Ticket Creation Failed !!!";
    }

    public Ticket fetchTicket(String ticketId, String token) {
        if(callRemoteApi(token)) {
            return ticketRepository.findById(ticketId).orElseThrow(()->
                    new CustomServiceException("Error While Fetching The Tickets "));
        }else{
            return null;
        }
    }


    public boolean callRemoteApi(String token) {
        String apiUrl = iamAppUrl + "/validate?token="+token;
        System.out.println("apiUrl"+ apiUrl);
        if(restTemplate.getForEntity(apiUrl, String.class).getBody().contains("true")){
            return true;
        }else{
            throw  new CustomServiceException("Token Expired Or Not Correct !!!");
        }
    }
}
