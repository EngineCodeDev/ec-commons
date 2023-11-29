package dev.enginecode.eccommons.structures.validation;

import dev.enginecode.eccommons.structures.model.DataModel;

import java.util.List;

public interface EntriesPayloadValidationStrategy {
    List<ValidationError> validate(EntriesPayload payload, DataModel dataModel);

    record ValidationError(String field, String errorCode, String defaultMessage) {
    }
}
