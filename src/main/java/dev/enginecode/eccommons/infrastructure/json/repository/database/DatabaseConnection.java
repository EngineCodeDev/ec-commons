package dev.enginecode.eccommons.infrastructure.json.repository.database;

public interface DatabaseConnection {
    <T> T get();
}
