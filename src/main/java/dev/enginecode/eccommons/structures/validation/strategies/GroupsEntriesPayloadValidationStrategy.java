package dev.enginecode.eccommons.structures.validation.strategies;

import dev.enginecode.eccommons.structures.model.DataModel;
import dev.enginecode.eccommons.structures.validation.EntriesPayload;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GroupsEntriesPayloadValidationStrategy implements EntriesPayloadValidationStrategy {
    @Override
    public List<ValidationError> validate(EntriesPayload payload, DataModel dataModel) {
        LinkedHashSet<String> unknownGroups = payload.groups().stream()
                .filter(group -> !dataModel.groupContents().containsKey(group))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return unknownGroups.isEmpty()
                ? List.of()
                : List.of(new ValidationError(
                        "groups","UNKNOWN_GROUPS",unknownGroups + " not found"
        ));
    }

}
