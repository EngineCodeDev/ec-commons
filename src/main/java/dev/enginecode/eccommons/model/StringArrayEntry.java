package dev.enginecode.eccommons.model;

import java.util.Arrays;

public class StringArrayEntry extends Entry<String[]> {
    public StringArrayEntry() {}
    public StringArrayEntry(String key, String[] value, String type, String info) {
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
