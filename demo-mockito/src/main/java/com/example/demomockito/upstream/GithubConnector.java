package com.example.demomockito.upstream;

import com.example.demomockito.config.UrlProperties;
import com.example.demomockito.upstream.dto.UserDto;
import com.fasterxml.jackson.databind.JsonNode;
import io.vavr.collection.List;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;


@Component
public class GithubConnector {

    private RestTemplate restTemplate;

    private UrlProperties urlProperties;

    protected GithubConnector(final RestTemplate restTemplate, UrlProperties urlProperties) {
        this.restTemplate = restTemplate;
        this.urlProperties = urlProperties;
    }

    public List<UserDto> getUsersByName(String userName) {
        JsonNode jsonData = restTemplate.getForObject(getUrl(userName), JsonNode.class);
        return ObjectUtils.isEmpty(jsonData) ? List.empty() : List.of(new UserDto(jsonData));
    }

    private String getUrl(String userName) {
        return urlProperties.getGithubUrl() + "users/" + userName;
    }
}
