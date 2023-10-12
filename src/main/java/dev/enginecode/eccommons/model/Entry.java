package dev.enginecode.eccommons.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Objects;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = StringEntry.class, name = "string"),
        @JsonSubTypes.Type(value = StringEntry.class, name = "enum"),
        @JsonSubTypes.Type(value = StringArrayEntry.class, name = "string_array"),
        @JsonSubTypes.Type(value = StringArrayEntry.class, name = "enum_array")
})
public abstract class Entry<T> {
    private String key;
    private T value;
    private String type;

    public Entry() {}

    public Entry(String key, T value, String type) {
        this.key = key;
        this.value = value;
        this.type = type;
    }

    public static <T> Entry<?> of(String key, T value, String type) {
        return switch (type.toLowerCase().trim()) {
            case "enum", "string" -> new StringEntry(key, (String) value, type);
            case "enum_array", "string_array" -> new StringArrayEntry(key, (String[]) value, type);
            default -> throw new RuntimeException();
        };
    }

    public String getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry<?> entry = (Entry<?>) o;
        return Objects.equals(key, entry.key) && Objects.equals(value, entry.value) && Objects.equals(type, entry.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value, type);
    }
}
