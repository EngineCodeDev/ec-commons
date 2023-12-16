package dev.enginecode.eccommons.structures.model;

public final class EnumEntry extends Entry<Entry<String>> {

    public EnumEntry() {}

    public EnumEntry(String key, StringEntry value, Type type, String info) {
        super(key, value, type, info);
    }
}
