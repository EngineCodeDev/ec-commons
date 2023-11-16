package dev.enginecode.eccommons.infrastructure.json.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.*;
import com.querydsl.sql.RelationalPathBase;
import dev.enginecode.eccommons.exception.JsonObjectProcessingException;
import dev.enginecode.eccommons.exception.ResourceNotFoundException;
import dev.enginecode.eccommons.exception.TableNotFoundException;
import dev.enginecode.eccommons.infrastructure.json.model.TableAnnotatedRecord;
import dev.enginecode.eccommons.infrastructure.json.model.TableName;
import dev.enginecode.eccommons.infrastructure.json.repository.database.DatabaseConnection;
import dev.enginecode.eccommons.infrastructure.json.repository.database.PostgresDatabaseConnection;
import org.postgresql.util.PGobject;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static dev.enginecode.eccommons.infrastructure.json.errors.InfrastructureErrorCode.*;

public abstract class DataRecordAbstractPostgresRepository<ID extends Serializable> implements JsonRepository<ID> {

    private final DatabaseConnection databaseConnection;

    public DataRecordAbstractPostgresRepository(DataSource dataSource) {
        databaseConnection = new PostgresDatabaseConnection(dataSource);
    }

    <R extends TableAnnotatedRecord<ID>> String getDataRecordById(ID id, Class<R> clazz) {
        String tableName = getTableName(clazz);
        QJsonRepository_DataRecord record = new QJsonRepository_DataRecord(tableName);
        return Optional.ofNullable(
                databaseConnection.getQueryFactory()
                        .select(record.data)
                        .from(record)
                        .where(record.id.eq(id))
                        .fetchOne()
        ).orElseThrow(() -> new ResourceNotFoundException(
                "Resource with id: '" + id + "' not found!", RESOURCE_NOT_FOUND
        ));
    }

    <R extends TableAnnotatedRecord<ID>> List<String> getDataRecordsByEntry(String entryKey, String entryValue, Class<R> clazz) {
        String tableName = getTableName(clazz);
        QJsonRepository_DataRecord record = new QJsonRepository_DataRecord(tableName);

        String conditionTemplate = """
                    {0} -> 'entries' @> (
                        SELECT jsonb_agg(jsonb_build_object('key', entry->'key', 'value', entry->'value'))
                        FROM jsonb_array_elements({1}->'entries') entry
                        WHERE entry->>'key' = {2} AND entry->>'value' = {3})""";

        return getDataRecordsWithCondition(
                Expressions.booleanTemplate(conditionTemplate, record.data, record.data, entryKey, entryValue),
                tableName
        );
    }

    <R extends TableAnnotatedRecord<ID>> List<String> getDataRecordsByColumn(String columnName, String value, Class<R> clazz) {
        String tableName = getTableName(clazz);
        StringPath column = Expressions.stringPath(columnName);
        return getDataRecordsWithCondition(column.eq(value), tableName);
    }

    <R extends TableAnnotatedRecord<ID>> List<String> getAllDataRecords(Class<R> clazz) {
        String tableName = getTableName(clazz);
        QJsonRepository_DataRecord record = new QJsonRepository_DataRecord(tableName);
        return databaseConnection.getQueryFactory()
                .select(record.data)
                .from(record)
                .fetch();
    }

    <R extends TableAnnotatedRecord<ID>> void saveDataRecord(JsonRepository.DataRecord<ID> dataRecord, Class<R> clazz) {
        String tableName = getTableName(clazz);
        RelationalPathBase<?> record = new RelationalPathBase<>(QJsonRepository_DataRecord.class, "", null, tableName);
        PGobject pgJsonb = getJsonb(dataRecord.data());

        databaseConnection.getQueryFactory()
                .insert(record)
                .values(dataRecord.id(), pgJsonb)
                .execute();
    }

    private List<String> getDataRecordsWithCondition(Predicate condition, String tableName) {
        QJsonRepository_DataRecord record = new QJsonRepository_DataRecord(tableName);

        return databaseConnection.getQueryFactory()
                .select(record.data)
                .from(record)
                .where(condition)
                .fetch();
    }


    private <R extends TableAnnotatedRecord<ID>> String getTableName(Class<R> clazz) {
        try {
            TableName tableName = clazz.getAnnotation(TableName.class);

            if (tableName.value().isBlank()) throw new TableNotFoundException(
                    "Lack of name in TableName annotation for class: '" + clazz.getName() + "'", TABLENAME_ANNOTATION_EMPTY
            );
            return tableName.value();
        } catch (NullPointerException e) {
            throw new TableNotFoundException(
                    "Lack of TableName annotation for class: '" + clazz.getName() + "'", TABLENAME_ANNOTATION_NOT_FOUND
            );
        }
    }

    private PGobject getJsonb(String jsonData) {
        PGobject pgObject = new PGobject();
        pgObject.setType("jsonb");
        try {
            pgObject.setValue(jsonData);
        } catch (SQLException e) {
            throw new JsonObjectProcessingException(e.getMessage(), CANNOT_SET_JSONB_TYPE);
        }
        return pgObject;
    }
}
