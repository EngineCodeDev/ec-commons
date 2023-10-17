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
        @JsonSubTypes.Type(value = StringEntry.class, name = "enum_key"),
        @JsonSubTypes.Type(value = EnumEntry.class, name = "enum"),
        @JsonSubTypes.Type(value = StringArrayEntry.class, name = "string_array"),
        @JsonSubTypes.Type(value = StringArrayEntry.class, name = "enum_key_array"),
        @JsonSubTypes.Type(value = EnumArrayEntry.class, name = "enum_array")
})
public abstract class Entry<T> {
    private String key;
    private T value;
    private String type;
    private String info;

    public Entry() {}

    public Entry(String key, T value, String type, String info) {
        this.key = key;
        this.value = value;
        this.type = type;
        this.info = info;
    }

    public static <T> Entry<?> of(String key, T value, String type, String info) {
        return switch (type.toLowerCase().trim()) {
            case "enum_key", "string" -> new StringEntry(key, (String) value, type, info);
            case "enum" -> new EnumEntry(key, (StringEntry) value, type, info);
            case "enum_key_array", "string_array" -> new StringArrayEntry(key, (String[]) value, type, info);
            case "enum_array" -> new EnumArrayEntry(key, (StringEntry[]) value, type, info);
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

    public String getInfo() {
        return info;
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

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entry<?> entry = (Entry<?>) o;

        if (!Objects.equals(key, entry.key)) return false;
        if (!Objects.equals(value, entry.value)) return false;
        if (!Objects.equals(type, entry.type)) return false;
        return Objects.equals(info, entry.info);
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        return result;
    }
}