package dev.enginecode.eccommons.structures.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.UUID;

public record DataModel(
        @JsonProperty(required = true)
        UUID id,
        @JsonProperty(required = true)
        LinkedHashMap<String, EntrySettings> entrySettings,
        @JsonProperty(required = true)
        LinkedHashMap<String, LinkedHashSet<String>> groupContents,
        @JsonProperty(required = true)
        LinkedHashMap<String, LinkedHashSet<Entry<?>>> enumOptions
) {

    public record EntrySettings(
            @JsonProperty(required = true)
            Entry.Type type,
            @JsonProperty(required = true)
            Format format,
            @JsonProperty(required = true)
            String optionsRef,
            @JsonProperty(required = true)
            boolean unique,
            @JsonProperty(required = true)
            boolean required,
            @JsonProperty(required = true)
            boolean editable,
            @JsonProperty(required = true)
            boolean visible
    ) {
        public enum Format {
            @JsonProperty("text")
            TEXT,
            @JsonProperty("dictionary")
            DICTIONARY,
            @JsonProperty("dynamic")
            DYNAMIC,
            @JsonProperty("url")
            URL,
            @JsonProperty("uuid")
            UUID
        }

    }
}
