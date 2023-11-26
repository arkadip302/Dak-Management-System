package com.tux.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTicketDTO {

    private String subject;

    private String state;

    private String source;

    private String priority;

    private List<String> comments;

    private String ticketId;

    private String assignedUser;

    private String createdUser;

    private String status;

    private String assignedDate;

    private Date updateTime;

    private Date createTime;

}
