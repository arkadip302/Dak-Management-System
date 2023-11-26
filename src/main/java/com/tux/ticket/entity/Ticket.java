package com.tux.ticket.entity;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(value ="ticket")
public class Ticket {

    @Id
    private String id;

    private String subject;

    private String state;

    private String source;

    private String priority;

    private List<String> comments;

    private String ticketId;

    private User assignedUser;

    private User createdUser;

    private String status;

    private String assignedDate;

    private Date updateTime;

    private Date createTime;

}
