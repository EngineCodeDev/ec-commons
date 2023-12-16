package dev.enginecode.eccommons.structures.validation.strategies;

import dev.enginecode.eccommons.structures.model.DataModel;
import dev.enginecode.eccommons.structures.validation.EntriesPayload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotBlankKeysEntriesPayloadValidationStrategy implements EntriesPayloadValidationStrategy {
    @Override
    public List<ValidationError> validate(EntriesPayload payload, DataModel dataModel) {
        boolean containBlankKey = payload.entries().stream()
                .anyMatch(entry -> entry.getKey() == null || entry.getKey().isBlank());

        return containBlankKey
                ? List.of(new ValidationError("entries", "INVALID_ENTRIES_KEYS",
                "entry key cannot be empty"))
                : List.of();
    }

}
