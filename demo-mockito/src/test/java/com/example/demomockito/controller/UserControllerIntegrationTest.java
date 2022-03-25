package com.example.demomockito.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.stop.Stop.stopQuietly;
import static org.mockserver.verify.VerificationTimes.exactly;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserControllerIntegrationTest {

    private static MockMvc mockMvc;

    private static ClientAndServer mockServer;

    @Autowired
    private WebApplicationContext context;

    @AfterEach
    public void stopProxy() {
        stopQuietly(mockServer);
    }

    @BeforeEach
    public void startMockServer() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        mockServer = startClientAndServer(9090);
        mockServer.reset();
    }

    @Test
    void giveUserInfoWhenUserNameCorrectThenReturnUserInfoSuccess() throws Exception {
        mockServer.when(
                        request().withPath("/users/tao")
                )
                .respond(
                        response().withHeaders(
                                        new Header(CONTENT_TYPE.toString(), MediaType.APPLICATION_JSON_VALUE)
                                )
                                .withBody("{\n" +
                                        "    \"login\": \"tao\",\n" +
                                        "    \"id\": 1446331,\n" +
                                        "    \"node_id\": \"MDQ6VXNlcjE0NDYzMzE=\",\n" +
                                        "    \"avatar_url\": \"https://avatars.githubusercontent.com/u/1446331?v=4\",\n" +
                                        "    \"gravatar_id\": \"\",\n" +
                                        "    \"url\": \"https://api.github.com/users/tao\",\n" +
                                        "    \"html_url\": \"https://github.com/tao\",\n" +
                                        "    \"followers_url\": \"https://api.github.com/users/tao/followers\",\n" +
                                        "    \"following_url\": \"https://api.github.com/users/tao/following{/other_user}\",\n" +
                                        "    \"gists_url\": \"https://api.github.com/users/tao/gists{/gist_id}\",\n" +
                                        "    \"starred_url\": \"https://api.github.com/users/tao/starred{/owner}{/repo}\",\n" +
                                        "    \"subscriptions_url\": \"https://api.github.com/users/tao/subscriptions\",\n" +
                                        "    \"organizations_url\": \"https://api.github.com/users/tao/orgs\",\n" +
                                        "    \"repos_url\": \"https://api.github.com/users/tao/repos\",\n" +
                                        "    \"events_url\": \"https://api.github.com/users/tao/events{/privacy}\",\n" +
                                        "    \"received_events_url\": \"https://api.github.com/users/tao/received_events\",\n" +
                                        "    \"type\": \"User\",\n" +
                                        "    \"site_admin\": false,\n" +
                                        "    \"name\": \"Daniel Shields\",\n" +
                                        "    \"company\": \"@llresearch \",\n" +
                                        "    \"blog\": \"www.elven.dev\",\n" +
                                        "    \"location\": \"Johannesburg, South Africa\",\n" +
                                        "    \"email\": null,\n" +
                                        "    \"hireable\": true,\n" +
                                        "    \"bio\": \"Recent MA Entrepreneurship graduate. Computer Scientist. Full stack developer. Designer. Life coach. Working on startups.\",\n" +
                                        "    \"twitter_username\": null,\n" +
                                        "    \"public_repos\": 77,\n" +
                                        "    \"public_gists\": 5,\n" +
                                        "    \"followers\": 8,\n" +
                                        "    \"following\": 1,\n" +
                                        "    \"created_at\": \"2012-02-17T11:22:35Z\",\n" +
                                        "    \"updated_at\": \"2021-11-28T12:29:45Z\"\n" +
                                        "}")
                );

        MvcResult result = mockMvc.perform(get("/user/tao").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("1446331"));
    }


    @Test
    public void shouldLoadSingleBook() throws Exception {
        mockServer.when(
                        request().withPath("/users/tao")
                )
                .respond(
                        response().withHeaders(
                                        new Header(CONTENT_TYPE.toString(), MediaType.APPLICATION_JSON_VALUE)
                                )
                                .withBody("")
                );

        MvcResult result = mockMvc.perform(get("/user/tao").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("oh, fail.."));
        mockServer.verify(request().withPath("/users/tao"), exactly(1));
    }


}