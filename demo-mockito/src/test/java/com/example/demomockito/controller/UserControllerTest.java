package com.example.demomockito.controller;

import com.example.demomockito.domain.user.User;
import com.example.demomockito.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Before
    public void setupMocks() {
        openMocks(this);
    }

    @Test
    public void givenExistUserNameWhenGetUserInfoThenReturnUserInfo() {
        UserService userService = Mockito.mock(UserService.class);
        userController = new UserController(userService);
        when(userService.getUserInfo("xxx")).thenReturn(Optional.of(new User("1234", "xxx", "User", "20219910", "0", "20220101", "xi'an")));
        when(userService.getUserInfo("yyy")).thenReturn(Optional.of(new User("1234", "yyy", "User", "20219910", "0", "20220101", "xi'an")));
        assertEquals(userController.getUserInfo("xxx").getReturnCode(), "0000");
        assertEquals(userController.getUserInfo("xxx").getBody().getName(), "xxx");

        verify(userService, times(2)).getUserInfo(ArgumentMatchers.any(String.class));
        verify(userService, times(2)).getUserInfo("xxx");
        verify(userService, atLeast(2)).getUserInfo("xxx");
        verify(userService, atMost(2)).getUserInfo("xxx");
        verify(userService, never()).getUserInfo("yyy");
    }

    @Test
    public void givenUserNameIsNullWhenGetUserInfoThenReturnUserInfoFail() {
        when(userService.getUserInfo("xxx")).thenAnswer(
                (Answer<Optional<User>>) invocation -> {
                    System.out.println("Arguments : " + Arrays.toString(invocation.getArguments()));
                    System.out.println("Method : " + invocation.getMethod().getName());
                    System.out.println("Mock : " + invocation.getMock());
                    return Optional.empty();
                }
        );

        when(userService.getUserInfo("yyy")).thenCallRealMethod();

        assertEquals(userController.getUserInfo("xxx").getReturnCode(), "9999");

        Assertions.assertThrows(NullPointerException.class, () -> {
            userController.getUserInfo("yyy");
        });

        verify(userService).getUserInfo("xxx");
    }
}
