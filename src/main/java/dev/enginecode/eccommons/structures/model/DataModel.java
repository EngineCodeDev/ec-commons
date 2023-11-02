package dev.enginecode.eccommons.structures.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public record DataModel(
        UUID id,
        LinkedHashMap<String, EntrySettings> entrySettings,
        LinkedHashMap<String, LinkedHashSet<String>> groupContents,
        LinkedHashMap<String, LinkedHashSet<Entry<?>>> enumOptions
) {

    public record EntrySettings(
            Entry.Type type,
            Format format,
            String optionsRef,
            boolean required,
            boolean readOnly
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

    }
}
