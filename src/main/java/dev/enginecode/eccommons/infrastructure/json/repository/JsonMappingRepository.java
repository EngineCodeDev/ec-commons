package dev.enginecode.eccommons.infrastructure.json.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.NonUniqueResultException;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.PostgreSQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import dev.enginecode.eccommons.exception.JsonNotDeserializedException;
import dev.enginecode.eccommons.exception.ResourceNotFoundException;
import dev.enginecode.eccommons.exception.TableNotFoundException;
import dev.enginecode.eccommons.infrastructure.json.model.TableAnnotatedRecord;
import dev.enginecode.eccommons.infrastructure.json.model.TableName;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.Serializable;

import static dev.enginecode.eccommons.infrastructure.json.errors.InfrastructureErrorCode.*;

@Repository
public class JsonMappingRepository<ID extends Serializable> implements JsonRepository<ID> {
    private final SQLQueryFactory queryFactory;
    private final ObjectMapper objectMapper = new ObjectMapper();

    JsonMappingRepository(DataSource dataSource) {
        SQLTemplates templates = new PostgreSQLTemplates();
        Configuration configuration = new Configuration(templates);
        this.queryFactory = new SQLQueryFactory(configuration, dataSource);
    }

    @Override
    public <R extends TableAnnotatedRecord> R findById(ID id, Class<R> clazz) {
        String tableName = getTableName(clazz);
        if (tableName.isBlank()) throw new TableNotFoundException(
                "Lack of name in TableName annotation for class: '" + clazz.getName() + "'", TABLENAME_ANNOTATION_EMPTY
        );

        String data = getDataById(id, tableName);

        if (data == null) {
            throw new ResourceNotFoundException(
                    "Resource with id: '" + id + "' not found!", RESOURCE_NOT_FOUND
            );
        }

        return deserializeJson(data, clazz);
    }

    public <R> R deserializeJson(String data, Class<R> clazz) {
        try {
            return objectMapper.readValue(data, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new JsonNotDeserializedException(e.getMessage(), CANNOT_DESERIALIZE_TO_GIVEN_CLASS);
        }
    }

    private String getDataById(ID id, String tableName) throws NonUniqueResultException {
        QJsonRepository_DataRecord record = new QJsonRepository_DataRecord(tableName);

        return queryFactory.select(record.data).from(record)
                .where(record.id.eq(id))
                .fetchOne();
    }

    private <R extends TableAnnotatedRecord> String getTableName(Class<R> clazz) {
        try {
            TableName tableName = clazz.getAnnotation(TableName.class);
            return tableName.value();
        } catch (NullPointerException e) {
            throw new TableNotFoundException(
                    "Lack of TableName annotation for class: '" + clazz.getName() + "'", TABLENAME_ANNOTATION_NOT_FOUND
            );
        }
    }

}



