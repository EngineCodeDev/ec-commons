package dev.enginecode.eccommons.infrastructure.json.repository

import dev.enginecode.eccommons.infrastructure.json.repository.mapping.JsonMapper
import dev.enginecode.eccommons.model.Entry
import dev.enginecode.eccommons.model.EnumEntry
import dev.enginecode.eccommons.model.StringArrayEntry
import dev.enginecode.eccommons.model.StringEntry
import spock.lang.Unroll

class JsonMapperTest extends JsonMapperDataTest {
    def setup() {
        jsonMapper = new JsonMapper()
    }

    @Unroll("#description")
    def "should deserialize json to an object of the given class"() {
        when:
        def deserializedObject = jsonMapper.read(jsonData, clazz)

        then:
        deserializedObject.class == clazz

        where:
        description                          | clazz                         | jsonData
        "object as Car class"                | Car.class                     | OBJECT_JSON
        "string entry as StringEntry"        | StringEntry.class             | STRING_ENTRY_JSON
        "enum_key entry as StringEntry"      | StringEntry.class             | ENUM_KEY_ENTRY_JSON
        "enum entry as EnumEntry"            | EnumEntry.class               | ENUM_ENTRY_JSON
        "string_array as StringArrayEntry"   | StringArrayEntry.class        | STRING_ARRAY_ENTRY_JSON
        "enum_key_array as StringArrayEntry" | StringArrayEntry.class        | ENUM_KEY_ARRAY_ENTRY_JSON
        "Entry<?> array as LinkedList"       | LinkedList<Entry<?>>.class    | ARRAY_OF_ENTRIES_JSON
        "Entry<?> array as HashSet"          | HashSet<Entry<?>>.class       | ARRAY_OF_ENTRIES_JSON
        "Entry<?> array as LinkedHashSet"    | LinkedHashSet<Entry<?>>.class | ARRAY_OF_ENTRIES_JSON
        "object as EntriesWrapper"           | EntriesWrapper.class          | ENTRIES_WRAPPER_JSON
        "object as EnumEntriesMapWrapper"    | EnumEntriesMapWrapper.class   | ENUM_ENTRIES_MAP_WRAPPER_JSON
        "nested enum entries as EnumEntry"   | EnumEntry.class               | NESTED_ENUM_ENTRIES_JSON
    }


}