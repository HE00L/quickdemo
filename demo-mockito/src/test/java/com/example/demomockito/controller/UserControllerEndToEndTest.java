package com.example.demomockito.controller;

import com.example.demomockito.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.model.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.verify.VerificationTimes.exactly;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@MockServerSettings(ports = {9090})
class UserControllerEndToEndTest {

    protected ClientAndServer mockServer;

    @Autowired
    RestTemplate restTemplate;

    @BeforeEach
    public void initMockClientService(ClientAndServer clientAndServer) {
        this.mockServer = clientAndServer;
        this.mockServer.reset();
    }


    @Test
    void givenUserNameCorrectWhenGetUserInfoThenReturnUserInfoSuccess() throws Exception {
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
        JsonNode result = restTemplate.getForObject("http://localhost:8989/user/tao", JsonNode.class);

        assertNotNull(result);

        assertEquals("1446331", result.get("body").get("id").asText());

        mockServer.verify(request().withPath("/users/tao"), exactly(1));
    }


    @Test
    public void givenUserNameNotExistInfoWhenGetUserInfoThenReturnUserInfoFail() throws Exception {
        mockServer.when(
                        request().withPath("/users/tao")
                )
                .respond(
                        response().withHeaders(
                                        new Header(CONTENT_TYPE.toString(), MediaType.APPLICATION_JSON_VALUE)
                                )
                                .withBody("")
                );


        JsonNode result = restTemplate.getForObject("http://localhost:8989/user/tao", JsonNode.class);

        assertNotNull(result);

        assertEquals("9999", result.get("returnCode").asText());

        mockServer.verify(request().withPath("/users/tao"), exactly(1));
    }


}