package dev.enginecode.eccommons.structures.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dev.enginecode.eccommons.exception.NotSupportedEntryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

import static dev.enginecode.eccommons.exception.EngineCodeExceptionGroup.INFRASTRUCTURE_ERROR;
import static dev.enginecode.eccommons.exception.NotSupportedEntryException.WRONG_TYPE;
import static dev.enginecode.eccommons.exception.NotSupportedEntryException.WRONG_TYPE_DETAILED;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        defaultImpl = StringEntry.class,
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = StringEntry.class, name = "string"),
        @JsonSubTypes.Type(value = StringEntry.class, name = "enum_key"),
        @JsonSubTypes.Type(value = EnumEntry.class, name = "enum"),
        @JsonSubTypes.Type(value = StringArrayEntry.class, name = "string_array"),
        @JsonSubTypes.Type(value = StringArrayEntry.class, name = "enum_key_array"),
        @JsonSubTypes.Type(value = EnumArrayEntry.class, name = "enum_array")
})
public abstract sealed class Entry<T> permits EnumEntry, EnumArrayEntry, StringEntry, StringArrayEntry {
    private static final Logger logger = LogManager.getLogger(Entry.class);
    private String key;
    private T value;
    private Type type;
    private String info;

    public Entry() {
    }

    public Entry(String key, T value, Type type, String info) {
        this.key = key;
        this.value = value;
        this.type = type;
        this.info = info;
    }

    public static <T> Entry<?> of(String key, T value, String type, String info) {
        if (type == null) {
            type = "string";
        }
        return switch (type.toLowerCase().trim()) {
            case "string" -> new StringEntry(key, (String) value, Type.STRING, info);
            case "enum_key" -> new StringEntry(key, (String) value, Type.ENUM_KEY, info);
            case "enum" -> new EnumEntry(key, (StringEntry) value, Type.ENUM, info);
            case "string_array" -> new StringArrayEntry(key, (String[]) value, Type.STRING_ARRAY, info);
            case "enum_key_array" -> new StringArrayEntry(key, (String[]) value, Type.ENUM_KEY_ARRAY, info);
            case "enum_array" -> new EnumArrayEntry(key, (StringEntry[]) value, Type.ENUM_ARRAY, info);
            default -> {
                logger.error(String.format(WRONG_TYPE_DETAILED, type.toLowerCase().trim() + " [in factory method of()]"));
                throw new NotSupportedEntryException(INFRASTRUCTURE_ERROR, WRONG_TYPE);
            }
        };
    }

    public enum Type {
        @JsonProperty("string")
        STRING,
        @JsonProperty("enum_key")
        ENUM_KEY,
        @JsonProperty("enum")
        ENUM,
        @JsonProperty("string_array")
        STRING_ARRAY,
        @JsonProperty("enum_key_array")
        ENUM_KEY_ARRAY,
        @JsonProperty("enum_array")
        ENUM_ARRAY
    }

    public String getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    public String getInfo() {
        return info;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry<?> entry = (Entry<?>) o;
        return Objects.equals(key, entry.key) && Objects.equals(value, entry.value) && Objects.equals(type, entry.type) && Objects.equals(info, entry.info);
    }

    @Override
    public int hashCode() {
        return key != null ? key.hashCode() : 0;
    }

    @Override
    public String toString() {
        return String.format("%s{'key='%s', value='%s', info='%s'}", this.getClass().getSimpleName(), key, value, info);
    }
}
