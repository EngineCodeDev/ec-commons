package dev.enginecode.eccommons.infrastructure.json.repository

import dev.enginecode.eccommons.infrastructure.json.model.TableAnnotatedRecord
import dev.enginecode.eccommons.model.Entry
import dev.enginecode.eccommons.model.EnumEntry
import dev.enginecode.eccommons.model.StringArrayEntry
import dev.enginecode.eccommons.model.StringEntry
import spock.lang.Specification
import spock.lang.Unroll

import javax.sql.DataSource

class JsonMappingRepositoryTest extends Specification {
    private DataSource dataSource = Mock(DataSource.class)
    private final def jsonMappingRepository = new JsonMappingRepository(dataSource)

    private static final def  OBJECT_JSON = """
        {
            "id": "e0743e28-448c-47d7-8617-7e083d430448",
            "name": "Audi"
        }
    """

    private static final def  STRING_ENTRY_JSON = """
        {
            "key": "entry_key",
            "value": "entry_value",
            "type": "string"
        }
    """

    private static final def  ENUM_KEY_ENTRY_JSON = """
        {
            "key": "entry_key",
            "value": "entry_value",
            "type": "enum_key"
        }
    """

    private static final def  ENUM_ENTRY_JSON = """
        {
            "key": "entry_key",
            "value": {
                "key": "sub_key",
                "value": "sub_value",
                "type": "string"
            },
            "type": "enum"
        }
    """

    private static final def  STRING_ARRAY_ENTRY_JSON = """
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

    private static final def  ENUM_KEY_ARRAY_ENTRY_JSON = """
        {
            "key": "entry_key",
            "value": [
                "value1",
                "value2",
                "value3"
            ],
            "type": "enum_key_array"
        }
    """

    private static final def  ARRAY_OF_ENTRIES_JSON = """
        [
            {
                "key": "entry_key1",
                "value": "entry_value1",
                "type": "string"
            },
            {
                "key": "entry_key2",
                "value": "entry_value2",
                "type": "enum_key"
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

    private static final def  ENTRIES_WRAPPER_JSON = """
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
                    "type": "enum_key"
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

    private static final def  ENUM_ENTRIES_MAP_WRAPPER_JSON = """
        {
            "id": "e0743e28-448c-47d7-8617-7e083d430448",
            "items": {
                "origin": [
                    {
                        "key": "entry_key1",
                        "value": {
                            "key": "sub_key1",
                            "value": "sub_value1"
                        },
                        "type": "enum"
                    },
                    {
                        "key": "entry_key2",
                        "value": {
                            "key": "sub_key2",
                            "value": "sub_value2",
                            "type": "string"
                        },
                        "type": "enum"
                    }
                ],
                "lifecycle": [
                    {
                        "key": "entry_key3",
                        "value": {
                            "key": "sub_key3",
                            "value": "sub_value3"
                        },
                        "type": "enum"
                    },
                    {
                        "key": "entry_key4",
                        "value": {
                            "key": "sub_key4",
                            "value": "sub_value4",
                            "type": "string"
                        },
                        "type": "enum"
                    }
                ],
                "other": [
                    {
                        "key": "entry_key5",
                        "value": [
                            {
                                "key": "sub_key51",
                                "value": "sub_value51"
                            },
                            {
                                "key": "sub_key52",
                                "value": "sub_value52"
                            },
                            {
                                "key": "sub_key53",
                                "value": "sub_value53",
                                "type": "string"
                            }
                        ],
                        "type": "enum_array"
                    }
                ]
            }
        }
    """

    private static final def  NESTED_ENUM_ENTRIES_JSON = """
        {
            "key": "entry_key",
            "value": {
                "key": "key-1",
                "value": {
                    "key": "key-2",
                    "value": {
                        "key": "key-3",
                        "value": "value-3",
                        "type": "string"
                    },
                    "type": "enum"
                },
                "type": "enum"
            },
            "type": "enum"
        }
    """

    @Unroll("#description")
    def "should deserialize json to an object of the given class"() {
        when:
        def deserializedObject = jsonMappingRepository.deserializeJson(jsonData, clazz)

        then:
        deserializedObject.class == clazz

        where:
        description                          | clazz                         | jsonData
        "object as Car class"                | Car.class                     |  OBJECT_JSON
        "string entry as StringEntry"        | StringEntry.class             |  STRING_ENTRY_JSON
        "enum_key entry as StringEntry"      | StringEntry.class             |  ENUM_KEY_ENTRY_JSON
        "enum entry as EnumEntry"            | EnumEntry.class               |  ENUM_ENTRY_JSON
        "string_array as StringArrayEntry"   | StringArrayEntry.class        |  STRING_ARRAY_ENTRY_JSON
        "enum_key_array as StringArrayEntry" | StringArrayEntry.class        |  ENUM_KEY_ARRAY_ENTRY_JSON
        "Entry<?> array as LinkedList"       | LinkedList<Entry<?>>.class    |  ARRAY_OF_ENTRIES_JSON
        "Entry<?> array as HashSet"          | HashSet<Entry<?>>.class       |  ARRAY_OF_ENTRIES_JSON
        "Entry<?> array as LinkedHashSet"    | LinkedHashSet<Entry<?>>.class |  ARRAY_OF_ENTRIES_JSON
        "object as EntriesWrapper"           | EntriesWrapper.class          |  ENTRIES_WRAPPER_JSON
        "object as EnumEntriesMapWrapper"    | EnumEntriesMapWrapper.class   |  ENUM_ENTRIES_MAP_WRAPPER_JSON
        "nested enum entries as EnumEntry"   | EnumEntry.class               |  NESTED_ENUM_ENTRIES_JSON
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

    @Override
    String toString() {
        return "Car{" + "id=" + id + ", name='" + name + '\'' + '}'
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

    @Override
    String toString() {
        return "EntriesWrapper{" + "id=" + id + ", entries=" + entries + '}'
    }
}

class EnumEntriesMapWrapper implements TableAnnotatedRecord {
    private UUID id
    private LinkedHashMap<String, LinkedHashSet<Entry<?>>> items

    UUID getId() {
        return id
    }

    LinkedHashMap<String, LinkedHashSet<Entry<?>>> getItems() {
        return items
    }

    @Override
    String toString() {
        return "EnumEntriesMapWrapper{" + "id=" + id + ", items=" + items + '}'
    }
}