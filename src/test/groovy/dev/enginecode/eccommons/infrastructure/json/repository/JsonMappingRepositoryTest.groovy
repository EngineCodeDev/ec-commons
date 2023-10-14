package dev.enginecode.eccommons.infrastructure.json.repository

import dev.enginecode.eccommons.infrastructure.json.model.TableAnnotatedRecord
import dev.enginecode.eccommons.model.Entry
import dev.enginecode.eccommons.model.StringArrayEntry
import dev.enginecode.eccommons.model.StringEntry
import spock.lang.Specification

import javax.sql.DataSource

class JsonMappingRepositoryTest extends Specification {
    private DataSource dataSource = Mock(DataSource.class)
    private final def jsonMappingRepository = new JsonMappingRepository(dataSource)

    private static final def GIVEN_OBJECT_JSON = """
        {
            "id": "e0743e28-448c-47d7-8617-7e083d430448",
            "name": "Audi"
        }
    """

    private static final def GIVEN_STRING_ENTRY_JSON = """
        {
            "key": "entry_key",
            "value": "entry_value",
            "type": "string"
        }
    """

    private static final def GIVEN_ENUM_ENTRY_JSON = """
        {
            "key": "entry_key",
            "value": "entry_value",
            "type": "enum"
        }
    """

    private static final def GIVEN_STRING_ARRAY_ENTRY_JSON = """
        {
            "key": "entry_key",
            "value": [
                "value1",
                "value2",
                "value3"
            ],
            "type": "string_array"
        }
    """

    private static final def GIVEN_ENUM_ARRAY_ENTRY_JSON = """
        {
            "key": "entry_key",
            "value": [
                "value1",
                "value2",
                "value3"
            ],
            "type": "enum_array"
        }
    """

    private static final def GIVEN_ARRAY_OF_ENTRIES_JSON = """
        [
            {
                "key": "entry_key1",
                "value": "entry_value1",
                "type": "string"
            },
            {
                "key": "entry_key2",
                "value": "entry_value2",
                "type": "enum"
            },
            {
                "key": "entry_key3",
                "value": [
                    "entry_value31",
                    "entry_value32",
                    "entry_value33",
                    "entry_value34"
                ],
                "type": "string_array"
            },
            {
                "key": "entry_key4",
                "value": "entry_value4",
                "type": "string"
            }
        ]
    """

    private static final def GIVEN_ENTRIES_WRAPPER_JSON = """
        {
            "id": "e0743e28-448c-47d7-8617-7e083d430448",
            "entries": [
                {
                    "key": "entry_key1",
                    "value": "entry_value1",
                    "type": "string"
                },
                {
                    "key": "entry_key2",
                    "value": "entry_value2",
                    "type": "enum"
                },
                {
                    "key": "entry_key3",
                    "value": [
                        "entry_value31",
                        "entry_value32",
                        "entry_value33",
                        "entry_value34"
                    ],
                    "type": "string_array"
                },
                {
                    "key": "entry_key4",
                    "value": "entry_value4",
                    "type": "string"
                }
            ]
        }
    """


    def "should deserialize json to an object of the given class"() {
        when:
        def deserializedObject = jsonMappingRepository.deserializeJson(jsonData, clazz)

        then:
        deserializedObject.class == clazz

        where:
        clazz                         | jsonData
        Car.class                     | GIVEN_OBJECT_JSON
        StringEntry.class             | GIVEN_STRING_ENTRY_JSON
        StringEntry.class             | GIVEN_ENUM_ENTRY_JSON
        StringArrayEntry.class        | GIVEN_STRING_ARRAY_ENTRY_JSON
        StringArrayEntry.class        | GIVEN_ENUM_ARRAY_ENTRY_JSON
        LinkedList<Entry<?>>.class    | GIVEN_ARRAY_OF_ENTRIES_JSON
        HashSet<Entry<?>>.class       | GIVEN_ARRAY_OF_ENTRIES_JSON
        LinkedHashSet<Entry<?>>.class | GIVEN_ARRAY_OF_ENTRIES_JSON
        EntriesWrapper.class          | GIVEN_ENTRIES_WRAPPER_JSON
    }


}

class Car implements TableAnnotatedRecord {
    private UUID id
    private String name

    UUID getId() {
        return id
    }

    String getName() {
        return name
    }
}

class EntriesWrapper implements TableAnnotatedRecord {
    private UUID id
    private LinkedHashSet<Entry<?>> entries

    UUID getId() {
        return id
    }

    LinkedHashSet<Entry<?>> getEntries() {
        return entries
    }
}