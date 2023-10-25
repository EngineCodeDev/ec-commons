package dev.enginecode.eccommons.infrastructure.json.repository.database;

import com.querydsl.sql.SQLQueryFactory;

public interface DatabaseConnection {
    SQLQueryFactory getQueryFactory();
}
