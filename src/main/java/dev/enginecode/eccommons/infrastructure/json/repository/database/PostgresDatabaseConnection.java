package dev.enginecode.eccommons.infrastructure.json.repository.database;

import com.querydsl.sql.Configuration;
import com.querydsl.sql.PostgreSQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;

import javax.sql.DataSource;

public class PostgresDatabaseConnection implements DatabaseConnection {
    private final SQLQueryFactory queryFactory;

    public PostgresDatabaseConnection(DataSource dataSource) {
        SQLTemplates templates = new PostgreSQLTemplates();
        Configuration configuration = new Configuration(templates);
        this.queryFactory = new SQLQueryFactory(configuration, dataSource);
    }

    public SQLQueryFactory get() {
        return queryFactory;
    }
}
