package dev.enginecode.eccommons.infrastructure.json.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.util.UUID;


@Entity
@Table(name = "ps_plant_entity")
public class DataRecord {
    @Id
    private UUID id;

    //@JdbcTypeCode(SqlTypes.JSON)
    //@Type(JsonType.class) //hibernate persistence
    //@Column(columnDefinition = "jsonb")       //hibernate persistence
    @Column(name = "data")
    private String name;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    void setId(UUID id) {
        this.id = id;
    }

    void setName(String name) {
        this.name = name;
    }
}
