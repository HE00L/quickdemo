package com.example.demomockito.upstream.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;

import java.util.Locale;

@AllArgsConstructor
public class UserDto {
    JsonNode data;

    String id;
    String name;
    String login;
    String created_at;
    String type;
    String updated_at;
    String location;

    public UserDto(JsonNode jsonNode) {
        this.data = jsonNode;
    }

    public String getId() {
        return getValue("Id");
    }

    public String getName() {
        return getValue("name");
    }

    public String getLogin() {
        return getValue("login");
    }

    public String getCreated_at() {
        return getValue("Created_at");
    }

    public String getType() {
        return getValue("Type");
    }

    public String getUpdated_at() {
        return getValue("Updated_at");
    }

    public String getLocation() {
        return getValue("location");
    }

    public Boolean getSite_admin() {
        return getBoolValue("site_admin");
    }

    private String getValue(String key) {
        return data.get(key.toLowerCase(Locale.ROOT)).asText();
    }

    private Boolean getBoolValue(String key) {
        return data.get(key).asBoolean();
    }
}
