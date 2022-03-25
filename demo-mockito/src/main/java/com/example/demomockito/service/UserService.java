package com.example.demomockito.service;

import com.example.demomockito.upstream.GithubConnector;
import com.example.demomockito.domain.user.User;
import io.vavr.collection.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    final GithubConnector connector;

    @Autowired
    public UserService(GithubConnector connector) {
        this.connector = connector;
    }

    public Optional<User> getUserInfo(final String userName) {
        List<User> users = connector.getUsersByName(userName)
                .filter(user -> !user.getSite_admin())
                .map(user -> User.builder()
                        .name(user.getName())
                        .login("**" + user.getLogin().substring(2))
                        .created_at(user.getCreated_at())
                        .updated_at(user.getUpdated_at().substring(2))
                        .type(user.getType())
                        .address("**" + user.getLocation().substring(2))
                        .id(user.getId())
                        .build());
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get());
    }
}
