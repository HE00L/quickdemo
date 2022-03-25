package com.example.demomockito.service;

import com.example.demomockito.upstream.GithubConnector;
import io.vavr.collection.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    GithubConnector connector;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void init() {
        userService = new UserService(connector);
    }

    @Test
    void givenUserNameCorrectWhenGetUserInfoThenReturnUserInfoSuccess() {
        when(connector.getUsersByName("tao")).thenReturn(List.empty());
        userService.getUserInfo("tao");
        userService.getUserInfo("tao");
    }

    @Test
    void givenUserNameIsNullWhenGetUserInfoThenReturnUserInfoFail() {
        when(connector.getUsersByName("tao")).thenReturn(List.empty());
        assertThat(userService.getUserInfo("tao").isPresent()).isFalse();

        given(connector.getUsersByName(any())).willThrow(new NullPointerException());
        Assertions.assertThrows(NullPointerException.class, () -> {
            userService.getUserInfo("tao");
        });
    }
}