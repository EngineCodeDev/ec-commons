package dev.enginecode.eccommons.infrastructure.json.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class PlantRecord {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("name")
    private String name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
