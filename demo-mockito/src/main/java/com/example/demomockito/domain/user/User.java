package com.example.demomockito.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class User {
    String id;
    String name;
    String login;
    String created_at;
    String type;
    String updated_at;
    String address;
}
