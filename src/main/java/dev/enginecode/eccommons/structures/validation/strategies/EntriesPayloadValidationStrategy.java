package dev.enginecode.eccommons.structures.validation.strategies;

import dev.enginecode.eccommons.structures.model.DataModel;
import dev.enginecode.eccommons.structures.validation.EntriesPayload;

import java.util.List;

public interface EntriesPayloadValidationStrategy {
    List<ValidationError> validate(EntriesPayload payload, DataModel dataModel);

    record ValidationError(String field, String errorCode, String defaultMessage) {
    }
}
