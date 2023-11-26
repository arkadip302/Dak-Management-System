package com.tux.ticket.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "user")
public class User {

    @Id
    private String id;

    private String name;

    private String email;

    private String password;

    private Role role;

    private Office office;
}
