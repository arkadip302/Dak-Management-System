package com.tux.ticket.service;


import com.tux.ticket.dto.CreateTicketDTO;
import com.tux.ticket.entity.Ticket;
import com.tux.ticket.entity.User;
import com.tux.ticket.exception.CustomServiceException;
import com.tux.ticket.repository.TicketRepository;
import com.tux.ticket.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Component
public class TicketService {
    private static ModelMapper modelMapper = new ModelMapper();

    @Autowired
    UserRepository userRepository;
    @Autowired
    TicketRepository ticketRepository;

    @Value("${app.iamAppUrl}")
    private String iamAppUrl;

    @Autowired
    private final RestTemplate restTemplate;

    public TicketService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public String createTicket(CreateTicketDTO createTicketDTO,String token) {

        if(!callRemoteApi(token).isEmpty() && createTicketDTO.getAssignedUser() != null || createTicketDTO.getCreatedUser() != null){
            Ticket ticket = modelMapper.map(createTicketDTO, Ticket.class);
            ticket.setCreatedUser(userRepository.findByName(createTicketDTO.getCreatedUser()).orElseThrow(() ->
                    new CustomServiceException("Not Able To Find Create User")));
            ticket.setAssignedUser(userRepository.findByName(createTicketDTO.getAssignedUser()).orElseThrow(() ->
                    new CustomServiceException("Not Able To Find Assigned User")));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(new Date());
            String ticketId= ticketRepository.findByCreatedUserOffice(ticket.getCreatedUser().getOffice().getOfficeName()).size()+1+"/DMS/"+ticket.getCreatedUser().getOffice().getOfficeName()+"/"+formattedDate;
            ticket.setTicketId(ticketId);
            ticketRepository.save(ticket);
            return ticketId;
        }
        return "Ticket Creation Failed !!!";
    }

    public Ticket fetchTicket(String ticketId, String token) {
        User user = userRepository.findByEmail(callRemoteApi(token)).orElseThrow(() ->
                new CustomServiceException("Not Able To Find Create User")) ;
        Ticket ticket = ticketRepository.findByTicketId(ticketId).orElseThrow(()->
                    new CustomServiceException("Error While Fetching The Tickets "));
        if(ticket.getCreatedUser().getOffice().getOfficeName().equals(user.getOffice().getOfficeName())){
            return ticket;
        }else{
            throw new CustomServiceException("Ticket Not Available");
        }
    }


    public String callRemoteApi(String token) {
        String apiUrl = iamAppUrl + "/validate?token="+token;
        System.out.println("apiUrl"+ apiUrl);
        if(!Objects.requireNonNull(restTemplate.getForEntity(apiUrl, String.class).getBody()).isEmpty()){
            return restTemplate.getForEntity(apiUrl, String.class).getBody();
        }else{
            throw  new CustomServiceException("Token Expired Or Not Correct !!!");
        }
    }
}
