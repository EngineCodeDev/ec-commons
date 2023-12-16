package dev.enginecode.eccommons.structures.validation.strategies;

import dev.enginecode.eccommons.structures.model.DataModel;
import dev.enginecode.eccommons.structures.validation.EntriesPayload;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TypeEntriesPayloadValidationStrategy implements EntriesPayloadValidationStrategy {
    @Override
    public List<ValidationError> validate(EntriesPayload payload, DataModel dataModel) {
        LinkedHashMap<String, DataModel.EntrySettings> dataModelEntrySettings = dataModel.entrySettings();
        List<ValidationError> validationErrors = new ArrayList<>();

        payload.entries().stream()
                .map(entry -> Map.entry(entry, Optional.ofNullable(dataModelEntrySettings.get(entry.getKey()))))
                .filter(mapEntry -> mapEntry.getValue().isPresent())
                .map(mapEntry -> Map.entry(mapEntry.getKey(), mapEntry.getValue().get().type()))
                .filter(mapEntry -> mapEntry.getKey().getType() != mapEntry.getValue())
                .forEach(mapEntry ->
                    validationErrors.add(new ValidationError(
                            "entries",
                            "INVALID_ENTRY_TYPE",
                            String.format(
                                    "[%s] should be of type %s but is %s",
                                    mapEntry.getKey().getKey(),
                                    mapEntry.getValue(),
                                    mapEntry.getKey().getType()
                            )
                    ))
                );

        return validationErrors;
    }
}
