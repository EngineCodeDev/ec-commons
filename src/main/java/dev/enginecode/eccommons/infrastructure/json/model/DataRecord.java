package dev.enginecode.eccommons.infrastructure.json.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;


@Entity
@Table(name = "ps_plant_entity")
public class DataRecord {
    @Id
    private UUID id;

    private String data;

    public UUID getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    void setId(UUID id) {
        this.id = id;
    }

    void setName(String data) {
        this.data = data;
    }
}
