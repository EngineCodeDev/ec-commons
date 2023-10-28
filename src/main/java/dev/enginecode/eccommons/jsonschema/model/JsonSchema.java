package dev.enginecode.eccommons.jsonschema.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public record JsonSchema(
        UUID id,
        LinkedHashMap<String, Settings> properties,
        LinkedHashMap<String, LinkedHashSet<Entry<?>>> options
) {

    public record Settings(
            Entry.Type type,
            Format format,
            String optionsRef,
            Boolean required,
            Boolean readOnly,
            List<Content> contents
    ) {
        public enum Format {
            @JsonProperty("dictionary")
            DICTIONARY,
            @JsonProperty("dynamic")
            DYNAMIC,
            @JsonProperty("url")
            URL,
            @JsonProperty("uuid")
            UUID
        }

        public record Content(
                Set<String> groups,
                Map<String, Settings> attributes
        ) {
        }

    }
}
