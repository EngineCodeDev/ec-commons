package dev.enginecode.eccommons.infrastructure.json.repository;

import com.querydsl.sql.SQLQueryFactory;
import dev.enginecode.eccommons.exception.ResourceNotFoundException;
import dev.enginecode.eccommons.exception.TableNotFoundException;
import dev.enginecode.eccommons.infrastructure.json.model.TableAnnotatedRecord;
import dev.enginecode.eccommons.infrastructure.json.model.TableName;
import dev.enginecode.eccommons.infrastructure.json.repository.database.DatabaseConnection;
import dev.enginecode.eccommons.infrastructure.json.repository.database.PostgresDatabaseConnection;
import dev.enginecode.eccommons.infrastructure.json.repository.mapping.JSONMapper;
import dev.enginecode.eccommons.infrastructure.json.repository.mapping.Mapping;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.Optional;

import static dev.enginecode.eccommons.infrastructure.json.errors.InfrastructureErrorCode.RESOURCE_NOT_FOUND;
import static dev.enginecode.eccommons.infrastructure.json.errors.InfrastructureErrorCode.TABLENAME_ANNOTATION_EMPTY;

@Repository
public class JsonMappingRepository<ID extends Serializable> implements JsonRepository<ID> {
    private final Mapping mapper = new JSONMapper();
    private final DatabaseConnection databaseConnection;

    JsonMappingRepository(DataSource dataSource) {
        databaseConnection = new PostgresDatabaseConnection(dataSource);
    }

    @Override
    public <R extends TableAnnotatedRecord> R findById(ID id, Class<R> clazz) {
        String tableName = getTableName(clazz);
        String data = getDataById(id, tableName, clazz);
        return mapper.deserialize(data, clazz);
    }

    private <R extends TableAnnotatedRecord> String getTableName(Class<R> clazz) {
        return Optional.ofNullable(clazz.getAnnotation(TableName.class))
                .map(TableName::value)
                .orElseThrow(() -> new TableNotFoundException("Lack of name in TableName annotation for class: '"
                        + clazz.getName() + "'", TABLENAME_ANNOTATION_EMPTY));
    }

    private <R> String getDataById(ID id, String tableName, Class<R> clazz) {
        if (tableName.isBlank()) {
            throw new ResourceNotFoundException("Resource with id: '" + id + "' not found!", RESOURCE_NOT_FOUND);
        }
        QJsonRepository_DataRecord record = new QJsonRepository_DataRecord(tableName);
        return Optional.ofNullable(((SQLQueryFactory) databaseConnection.get())
                .select(record.data)
                .from(record)
                .where(record.id.eq(id))
                .fetchOne()
        ).orElseThrow(() -> new ResourceNotFoundException("Resource with id: '" + id + "' not found!",
                RESOURCE_NOT_FOUND));
    }
}
