package dev.enginecode.eccommons.infrastructure.json.repository;

import dev.enginecode.eccommons.exception.ResourceNotFoundException;
import dev.enginecode.eccommons.infrastructure.json.model.TableAnnotatedRecord;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface JsonRepository<ID extends Serializable> {

    /**
     * Finds a record of a specified type {@code R} from the database table associated with the given {@code clazz},
     * based on the provided record identifier {@code id}.
     *
     * @param id    The identifier of the record to be retrieved.
     * @param clazz The class representing the type of record to be retrieved.
     *              It must be a subtype of {@link TableAnnotatedRecord}.
     * @return The record with the specified identifier.
     * @throws ResourceNotFoundException if record with the given {@code id} not found.
     */
    <R extends TableAnnotatedRecord<ID>> R findById(ID id, Class<R> clazz);

    /**
     * Finds records of a specified type {@code R} from the database table associated with the given {@code clazz},
     * where the specified virtual column is 'like' the provided searchPhrase ignoring case.
     *
     * <p>A "virtual column" in this context refers to a computed or derived column, rather than a physical column
     * directly stored in the database. The method allows searching for records based on the computed values
     * of such virtual columns.</p>
     *
     * @param columnName   The name of the virtual column to be matched.
     * @param searchPhrase The search phrase to be matched within the specified virtual column.
     * @param clazz        The class representing the type of records to be retrieved.
     *                     It must be a subtype of {@link TableAnnotatedRecord}.
     * @return A list of records where the specified virtual column matches the provided value
     *         or an empty list if no records found for the given criteria.
     */
    <R extends TableAnnotatedRecord<ID>> List<R> findByVirtualColumnLikeIgnoreCase(String columnName, String searchPhrase, Class<R> clazz);

    /**
     * Finds records of a specified type {@code R} from the database table associated with the given {@code clazz},
     * where the specified virtual column matches the provided value.
     *
     * <p>A "virtual column" in this context refers to a computed or derived column, rather than a physical column
     * directly stored in the database. The method allows searching for records based on the computed values
     * of such virtual columns.</p>
     *
     * @param columnName The name of the virtual column to be matched.
     * @param value      The value to be matched within the specified virtual column.
     * @param clazz      The class representing the type of records to be retrieved.
     *                   It must be a subtype of {@link TableAnnotatedRecord}.
     * @return A list of records where the specified virtual column matches the provided value
     *         or an empty list if no records found for the given criteria.
     */
    <R extends TableAnnotatedRecord<ID>> List<R> findByVirtualColumn(String columnName, String value, Class<R> clazz);

    /**
     * Finds records of a specified type {@code R} from the database table associated with the given {@code clazz},
     * where the JSONB entries contain a key-value pair matching the specified criteria.
     *
     * @param entryKey   The key to be matched within the JSONB entries.
     * @param entryValue The value to be matched within the JSONB entries.
     * @param clazz      The class representing the type of records to be retrieved.
     *                   It must be a subtype of {@link TableAnnotatedRecord}.
     * @return A list of records where the specified key-value pair is present in the JSONB entries
     *         or an empty list if no records found for the given criteria.
     */
    <R extends TableAnnotatedRecord<ID>> List<R> findByEntryValue(String entryKey, String entryValue, Class<R> clazz);

    /**
     * Retrieves all records of a specified type {@code R} from the database table associated with the given {@code clazz}.
     *
     * @param clazz The class representing the type of records to be retrieved.
     *              It must be a subtype of {@link TableAnnotatedRecord}.
     * @return A list containing all records of the specified type from the database table
     *         or an empty list if no records found.
     */
    <R extends TableAnnotatedRecord<ID>> List<R> findAll(Class<R> clazz);

    /**
     * Saves a record of a specified type {@code R} to the database table associated with the given {@code clazz}.
     *
     * <p>The record provided as {@code object} will be inserted into the database if it is a new record (no existing
     * identifier), or updated if it already exists in the database.</p>
     *
     * @param <R> The type of the record to be saved, extending {@code TableAnnotatedRecord}.
     *           This type parameter is specific to the type of records stored in the database table.
     * @param object The record to be saved or updated in the database.
     * @param clazz The class representing the type of record to be saved.
     *              It must be a subtype of {@code TableAnnotatedRecord}.
     * @throws SQLException when duplicated key violates unique constraint
     */
    <R extends TableAnnotatedRecord<ID>> void save(R object, Class<R> clazz);


    /**
     * A record representing serialized JSON data stored in a repository, intended for use with the {@link JsonRepository}.
     *
     * @param <ID> The type of the record identifier, extending {@link Serializable}.
     */
    @Entity
    record DataRecord<ID extends Serializable>(@Id ID id, String data) {
        public DataRecord() {
            this(null, null);
        }
    }
}
