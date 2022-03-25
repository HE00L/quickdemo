package com.example.demomockito.upstream;

import com.example.demomockito.config.UrlProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.collection.List;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class GithubConnectorTest {

    private GithubConnector githubConnector;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private UrlProperties urlProperties;

    ObjectMapper objectMapper;

    @Before
    public void setupMocks() {
        openMocks(this);
        objectMapper = new ObjectMapper();
        githubConnector = new GithubConnector(restTemplate, urlProperties);
    }

    @Test
    public void givenUserNameNotExistWhenGetUsersByNameThenReturnEmptyData() {
        when(urlProperties.getGithubUrl()).thenReturn("http://localhost:0000/").thenReturn("http://localhost:0001/");

        when(restTemplate.getForObject("http://localhost:0000/users/tao", JsonNode.class))
                .thenReturn(objectMapper.createObjectNode().put("id", "1234"));

        when(restTemplate.getForObject("http://localhost:0001/users/tao", JsonNode.class)).thenReturn(null);

        Assertions.assertThat(githubConnector.getUsersByName("tao").get(0).getId()).isEqualTo("1234");
        Assertions.assertThat(githubConnector.getUsersByName("tao")).isEqualTo(List.empty());
    }

    @Test
    public void givenUserNameWhenGetUsersByNameThenReturnSuccess() {
        doReturn("http://localhost:0000/").doReturn("http://localhost:0001").when(urlProperties).getGithubUrl();

        when(restTemplate.getForObject("http://localhost:0000/users/tao", JsonNode.class))
                .thenReturn(objectMapper.createObjectNode().put("id", "1234")).thenReturn(objectMapper.createObjectNode().put("id", "2345"));

        when(restTemplate.getForObject("http://localhost:0000/users/tao", JsonNode.class))
                .thenReturn(objectMapper.createObjectNode().put("id", "2345"));

        Assertions.assertThat(githubConnector.getUsersByName("tao").get(0).getId()).isEqualTo("1234");
        Assertions.assertThat(githubConnector.getUsersByName("tao").get(0).getId()).isEqualTo("2345");
    }
}
