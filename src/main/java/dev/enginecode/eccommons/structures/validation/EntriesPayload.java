package dev.enginecode.eccommons.structures.validation;

import dev.enginecode.eccommons.structures.model.Entry;

import java.util.LinkedHashSet;

public interface EntriesPayload {
    LinkedHashSet<String> groups();

    LinkedHashSet<Entry<?>> entries();
}
