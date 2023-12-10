package dev.enginecode.eccommons.structures.validation

import dev.enginecode.eccommons.structures.model.DataModel
import dev.enginecode.eccommons.structures.model.Entry
import spock.lang.Specification

class TypeEntriesPayloadValidationStrategyTest extends Specification {
    private static final def dataModel = new DataModel(
            UUID.randomUUID(),
            Map.of("entry", new DataModel.EntrySettings(Entry.Type.ENUM, DataModel.EntrySettings.Format.DICTIONARY,
                    "", false, false, false)
            ) as LinkedHashMap, Map.of() as LinkedHashMap,
            Map.of() as LinkedHashMap
    )

    private static final def strategy = new TypeEntriesPayloadValidationStrategy()

    def "should validate entries payload"() {
        given:
        EntriesPayload entriesPayload = Mock(EntriesPayload)

        when:
        entriesPayload.entries() >> new LinkedHashSet<Entry<?>>(Set.of(Entry.of("entry", "value", "string", "")))
        def validationErrors = strategy.validate(entriesPayload, dataModel)

        then:
        validationErrors.size() == 1
        validationErrors.get(0).field() == "entries"
        validationErrors.get(0).errorCode() == "INVALID_ENTRY_TYPE"
        validationErrors.get(0).defaultMessage() == "[entry] should be of type ENUM but is STRING"

    }
}
