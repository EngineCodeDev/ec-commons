package dev.enginecode.eccommons.structures.validation.strategies

import dev.enginecode.eccommons.structures.model.DataModel
import dev.enginecode.eccommons.structures.model.Entry
import dev.enginecode.eccommons.structures.validation.EntriesPayload
import spock.lang.Specification

class FormatEntriesPayloadValidationStrategyTest extends Specification {
    private static final def dataModel = new DataModel(
            UUID.randomUUID(),
            Map.of(
                    "entry_url",
                    new DataModel.EntrySettings(Entry.Type.STRING, DataModel.EntrySettings.Format.URL,
                            "", false, false, false, false),
                    "entry_uuid",
                    new DataModel.EntrySettings(Entry.Type.STRING, DataModel.EntrySettings.Format.UUID,
                            "", false, false, false, false)
            ) as LinkedHashMap, Map.of() as LinkedHashMap,
            Map.of() as LinkedHashMap
    )

    private static final def strategy = new FormatEntriesPayloadValidationStrategy()

    def "should validate entries payload"() {
        given:
        EntriesPayload entriesPayload = Mock(EntriesPayload)

        when:
        entriesPayload.entries() >> new LinkedHashSet<Entry<?>>(Set.of(Entry.of(entryKey, entryValue, entryType, "")))
        def validationErrors = strategy.validate(entriesPayload, dataModel)

        then:
        validationErrors.size() == 1
        validationErrors.get(0).field() == "entries"
        validationErrors.get(0).errorCode() == "INVALID_ENTRY_FORMAT"
        validationErrors.get(0).defaultMessage() == "[$entryKey] should be in $format format"

        where:
        entryKey        | entryValue                              | entryType | format
        "entry_url"     | "http://www.test.com/all/2"             | "string"  | DataModel.EntrySettings.Format.URL
        "entry_uuid"    | "x ab91f98-b972-43af-a6d9-3f4cf00062bb" | "string"  | DataModel.EntrySettings.Format.UUID
    }
}
