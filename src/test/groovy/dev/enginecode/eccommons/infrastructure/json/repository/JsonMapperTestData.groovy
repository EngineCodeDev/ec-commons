package dev.enginecode.eccommons.infrastructure.json.repository

import dev.enginecode.eccommons.infrastructure.json.model.TableAnnotatedRecord
import dev.enginecode.eccommons.infrastructure.json.repository.mapping.JsonMapper
import dev.enginecode.eccommons.structures.model.Entry
import spock.lang.Specification

abstract class JsonMapperTestData extends Specification {
    public JsonMapper jsonMapper

    static final def OBJECT_JSON = """
        {
            "id": "e0743e28-448c-47d7-8617-7e083d430448",
            "name": "Audi"
        }
    """

    static final def STRING_ENTRY_JSON = """
        {
            "key": "entry_key",
            "value": "entry_value",
            "type": "string"
        }
    """

    static final def ENUM_KEY_ENTRY_JSON = """
        {
            "key": "entry_key",
            "value": "entry_value",
            "type": "enum_key"
        }
    """

    static final def ENUM_ENTRY_JSON = """
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

    static final def STRING_ARRAY_ENTRY_JSON = """
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

    static final def ENUM_KEY_ARRAY_ENTRY_JSON = """
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

    static final def ARRAY_OF_ENTRIES_JSON = """
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

    static final def ENTRIES_WRAPPER_JSON = """
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

    static final def ENUM_ENTRIES_MAP_WRAPPER_JSON = """
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

    static final def NESTED_ENUM_ENTRIES_JSON = """
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

    static final def DATA_MODEL = """
        {
          "id": "8fd1980d-3151-4d00-8495-5a10bf8e7099",
          "entrySettings": {
            "status": {"type":  "enum_key", "format": "dictionary", "optionsRef": null, "unique": false, "required": true, "editable": false, "visible": true},
            "species": {"type": "string", "format": "text", "optionsRef": null, "unique": true, "required": true, "editable": true, "visible": true},
            "genus": {"type": "string", "format": "text", "optionsRef": null, "unique": false, "required": true, "editable": true, "visible": true},
            "family": {"type": "string", "format": "text", "optionsRef": null, "unique": false, "required": true, "editable": true, "visible": true},
            "commonNames": {"type": "string_array", "format": "text", "optionsRef": null, "unique": false, "required": true, "editable": false, "visible": true},
            "lifecycle": {"type": "enum_key", "format": "dictionary", "optionsRef": "lifecycle", "unique": false, "required": true, "editable": false, "visible": true},
            "origin": {"type": "enum_key_array", "format": "dictionary", "optionsRef": "origin", "unique": false, "required": true, "editable": false, "visible": true},
            "something": {"type": "enum_key", "format": "dynamic", "optionsRef": "/app-resources/somethings", "unique": false, "required": true, "editable": false, "visible": true},
            "wikiLinks": {"type": "string", "format": "url", "optionsRef": null, "unique": false, "required": true, "editable": false, "visible": true},
            "imageSource": {"type": "string", "format": "url", "optionsRef": null, "unique": false, "required": true, "editable": true, "visible": true},
            "somethingNew": {"type": "string", "format": "url", "optionsRef": null, "unique": false, "required": true, "editable": true, "visible": true}
          },
          "groupContents": {
            "potted plant": ["genus", "family", "commonNames", "lifecycle", "origin", "something", "wikiLinks", "imageSource", "somethingNew"],
            "trees": ["genus", "family", "commonNames"],
            "ground plant": ["genus", "family", "commonNames"]
          },
          "enumOptions": {
            "origin": [
              {"key": "origin_asia", "value": "Asia", "info": ""},
              {"key": "origin_europe", "value": "Europe", "info": ""},
              {"key": "origin_southamerica", "value": "South America", "info": ""},
              {"key": "origin_northamerica", "value": "North America", "info": ""},
              {"key": "origin_africa", "value": "Africa", "info": ""},
              {"key": "origin_australia", "value": "Australia", "info": ""},
              {"key": "origin_antarctica", "value": "Antarctica", "info": ""}
            ],
            "lifecycle": [
              {"key": "lifecycle_annual", "value": "Annual", "info": ""},
              {"key": "lifecycle_perennial", "value": "Perennial", "info": ""},
              {"key": "lifecycle_biennial", "value": "Biennial", "info": ""}
            ],
            "substrate": [
              {"key": "substrate_soil", "value": "Soil", "info": ""},
              {"key": "substrate_perlite", "value": "Perlite", "info": ""},
              {"key": "substrate_vermiculite", "value": "Vermiculite", "info": ""}
            ]
          }
        }
    """
}


record Car(UUID id, String name) implements TableAnnotatedRecord<UUID> {
    @Override
    String toString() {
        return "Car{" + "id=" + id + ", name='" + name + '\'' + '}'
    }
}

record EntriesWrapper(UUID id, LinkedHashSet<Entry<?>> entries) implements TableAnnotatedRecord<UUID> {
    @Override
    String toString() {
        return "EntriesWrapper{" + "id=" + id + ", entries=" + entries + '}'
    }
}

record EnumEntriesMapWrapper(UUID id, LinkedHashMap<String, LinkedHashSet<Entry<?>>> items)
        implements TableAnnotatedRecord<UUID> {
    @Override
    String toString() {
        return "EnumEntriesMapWrapper{" + "id=" + id + ", items=" + items + '}'
    }
}