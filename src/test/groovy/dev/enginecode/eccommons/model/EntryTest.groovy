package dev.enginecode.eccommons.model

import spock.lang.Specification

class EntryTest extends Specification {

    def "should create Entry with value of type string"() {
        given:
        String key = "Key of entry"
        String value = "Value of string entry"

        when:
        Entry entry = Entry.of(key, value, type)

        then:
        entry.getValue() instanceof String

        where:
        type     | _
        "String" | _
        "enum "  | _
    }

    def "should create Entry with value of type string_array"() {
        given:
        String key = "Key of string array entry"
        String type = "String_Array"
        String[] value = ["Value 1", "Value 2", "Value 3"]

        when:
        Entry entry = Entry.of(key, value, type)

        then:
        entry.getValue() instanceof String[]
    }

}
