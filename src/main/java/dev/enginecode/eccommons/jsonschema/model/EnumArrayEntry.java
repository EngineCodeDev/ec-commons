package dev.enginecode.eccommons.jsonschema.model;

import java.util.Arrays;

public class EnumArrayEntry extends Entry<Entry<String>[]> {
    public EnumArrayEntry() {}
    public EnumArrayEntry(String key, StringEntry[] value, Type type, String info) {
        super(key, value, type, info);
    }

    @Override
    public String toString() {
        String value = Arrays.toString(super.getValue());
        return String.format(
                "%s{'key='%s', value='%s', info='%s'}", this.getClass().getSimpleName(), getKey(), value, getInfo()
        );
    }
}
