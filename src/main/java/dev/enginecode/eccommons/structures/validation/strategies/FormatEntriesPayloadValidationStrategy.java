package dev.enginecode.eccommons.structures.validation.strategies;

import dev.enginecode.eccommons.structures.model.*;
import dev.enginecode.eccommons.structures.validation.EntriesPayload;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;

@Component
public class FormatEntriesPayloadValidationStrategy implements EntriesPayloadValidationStrategy {
    @Override
    public List<ValidationError> validate(EntriesPayload payload, DataModel dataModel) {
        LinkedHashMap<String, DataModel.EntrySettings> dataModelEntrySettings = dataModel.entrySettings();
        List<ValidationError> validationErrors = new ArrayList<>();

        payload.entries().stream()
                .filter(entry -> entry.getKey() != null && entry.getValue() != null)
                .map(entry -> Map.entry(entry, Optional.ofNullable(dataModelEntrySettings.get(entry.getKey()))))
                .filter(mapEntry -> mapEntry.getValue().isPresent())
                .map(mapEntry -> Map.entry(mapEntry.getKey(), mapEntry.getValue().get().format()))
                .filter(entryFormat -> !isFormatCorrect(entryFormat.getKey(), entryFormat.getValue()))
                .forEach(entryFormat ->
                        validationErrors.add(new ValidationError(
                                "entries",
                                "INVALID_ENTRY_FORMAT",
                                String.format(
                                        "[%s] should be in %s format", entryFormat.getKey().getKey(), entryFormat.getValue()
                                )
                        )));

        return validationErrors;
    }


    private boolean isFormatCorrect(Entry<?> entry, DataModel.EntrySettings.Format format) {
        return switch (format) {
            case TEXT, DICTIONARY, DYNAMIC -> true;
            case URL -> hasCorrectFormat(entry, this::isValidURL);
            case UUID -> hasCorrectFormat(entry, this::isValidUUID);
        };
    }

    private boolean hasCorrectFormat(Entry<?> entry, Predicate<String> stringPredicate) {
        return switch (entry) {
            case StringEntry s -> stringPredicate.test(s.getValue());
            case EnumEntry e -> hasCorrectFormat(e.getValue(), stringPredicate);
            case StringArrayEntry sa -> Arrays.stream(sa.getValue()).allMatch(stringPredicate);
            case EnumArrayEntry ea -> Arrays.stream(ea.getValue()).allMatch(arrayEntry -> hasCorrectFormat(arrayEntry, stringPredicate));
        };
    }


    private boolean isValidURL(String value) {
        try {
            var url = new URL(value);
            url.toURI();
            return url.getProtocol().equals("https");
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

    private boolean isValidUUID(String value) {
        try {
            UUID.fromString(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
