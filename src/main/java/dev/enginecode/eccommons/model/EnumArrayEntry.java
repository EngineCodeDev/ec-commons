package dev.enginecode.eccommons.model;

public class EnumArrayEntry extends Entry<Entry<String>[]> {
    public EnumArrayEntry() {}
    public EnumArrayEntry(String key, StringEntry[] value, String type, String info) {
        super(key, value, type, info);
    }
}
