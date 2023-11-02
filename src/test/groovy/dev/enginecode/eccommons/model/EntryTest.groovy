package dev.enginecode.eccommons.model

import dev.enginecode.eccommons.structures.model.Entry
import dev.enginecode.eccommons.structures.model.StringEntry
import spock.lang.Specification

class EntryTest extends Specification {

    def "should create Entry with value of type string"() {
        given:
        String key = "Key of entry"
        String value = "Value of string entry"
        String info = ""

        when:
        Entry entry = Entry.of(key, value, type, info)

        then:
        entry.getValue() instanceof String

        where:
        type        | _
        "String"    | _
        "enum_key " | _
        null        | _
    }

    def "should create Entry with value of type string_array"() {
        given:
        String key = "Key of string array entry"
        String type = "String_Array"
        String[] value = ["Value 1", "Value 2", "Value 3"]
        String info = ""

        when:
        Entry entry = Entry.of(key, value, type, info)

        then:
        entry.getValue() instanceof String[]
    }

    def "should create Entry with value of type enum"() {
        given:
        String key = "Key of entry"
        StringEntry value = new StringEntry("sub_key", "sub_value", Entry.Type.STRING, "")
        String type = "enum"
        String info = ""

        when:
        Entry entry = Entry.of(key, value, type, info)

        then:
        entry.getValue() instanceof StringEntry
    }

    def "should create Entry with value of type enum_array"() {
        given:
        String key = "Key of entry"
        StringEntry[] value = [
                new StringEntry("sub_key1", "sub_value1", Entry.Type.STRING, "1"),
                new StringEntry("sub_key2", "sub_value2", Entry.Type.STRING, "2"),
                new StringEntry("sub_key3", "sub_value3", Entry.Type.STRING, "3"),
                new StringEntry("sub_key4", "sub_value4", Entry.Type.STRING, "4")
        ]
        String type = "enum_array"
        String info = ""

        when:
        Entry entry = Entry.of(key, value, type, info)

        then:
        entry.getValue() instanceof StringEntry[]
    }

}
