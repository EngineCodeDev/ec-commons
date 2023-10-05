package dev.enginecode.eccommons.model;

import java.util.Objects;

public class Entry<T> {
    private final String name;
    private final T value;
    private final String type;

    Entry(String name, T value, String type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public static <T> Entry<?> of(String name, T value, String type) {
        return switch (type.toLowerCase().trim()) {
            case "enum", "string" -> new StringEntry(name, (String) value, type);
            case "string_array" -> new StringArrayEntry(name, (String[]) value, type);
            default -> throw new RuntimeException();
        };
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry<?> entry = (Entry<?>) o;
        return Objects.equals(name, entry.name) && Objects.equals(value, entry.value) && Objects.equals(type, entry.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, type);
    }
}

class StringEntry extends Entry<String> {
    StringEntry(String name, String value, String type) {
        super(name, value, type);
    }
}
class StringArrayEntry extends Entry<String[]> {
    StringArrayEntry(String name, String[] value, String type) {
        super(name, value, type);
    }
}