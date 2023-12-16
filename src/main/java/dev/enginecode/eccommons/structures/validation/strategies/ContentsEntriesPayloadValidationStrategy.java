package dev.enginecode.eccommons.structures.validation.strategies;

import dev.enginecode.eccommons.structures.model.DataModel;
import dev.enginecode.eccommons.structures.model.Entry;
import dev.enginecode.eccommons.structures.validation.EntriesPayload;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ContentsEntriesPayloadValidationStrategy implements EntriesPayloadValidationStrategy {
    @Override
    public List<ValidationError> validate(EntriesPayload payload, DataModel dataModel) {
        Set<String> groupsContents = getGroupContentsByPayloadGroups(dataModel, payload.groups());

        LinkedHashSet<String> unexpectedContents = payload.entries().stream()
                .map(Entry::getKey)
                .filter(entryKey -> !groupsContents.contains(entryKey))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return unexpectedContents.isEmpty()
                ? List.of()
                : unexpectedContents.stream().map(unexpectedEntry -> new ValidationError(
                "entries", "UNEXPECTED_CONTENTS",
                String.format("[%s] not expected for groups %s", unexpectedEntry, payload.groups())
        )).toList();
    }


    private Set<String> getGroupContentsByPayloadGroups(DataModel dataModel, Set<String> groups) {
        LinkedHashMap<String, Set<String>> groupContents = dataModel.groupContents().entrySet().stream()
                .filter(groupContent -> groups.contains(groupContent.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (key1, key2) -> key1, LinkedHashMap::new));

        return groupContents.values().stream().flatMap(Set::stream).collect(Collectors.toSet());
    }

}
