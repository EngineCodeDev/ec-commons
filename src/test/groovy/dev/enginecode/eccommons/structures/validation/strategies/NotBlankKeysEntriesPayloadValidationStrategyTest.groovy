package dev.enginecode.eccommons.structures.validation.strategies

import dev.enginecode.eccommons.structures.model.DataModel
import dev.enginecode.eccommons.structures.model.Entry
import dev.enginecode.eccommons.structures.validation.EntriesPayload
import spock.lang.Specification

class NotBlankKeysEntriesPayloadValidationStrategyTest extends Specification {
    private static final def dataModel = new DataModel(
            UUID.randomUUID(),
            Map.of() as LinkedHashMap, Map.of() as LinkedHashMap,
            Map.of() as LinkedHashMap
    )

    private static final def strategy = new NotBlankKeysEntriesPayloadValidationStrategy()

    def "should validate entries payload"() {
        given:
        EntriesPayload entriesPayload = Mock(EntriesPayload)

        when:
        entriesPayload.entries() >> new LinkedHashSet<Entry<?>>(Set.of(Entry.of(entryKey, "", "string", "")))
        def validationErrors = strategy.validate(entriesPayload, dataModel)

        then:
        validationErrors.size() == 1
        validationErrors.get(0).field() == "entries"
        validationErrors.get(0).errorCode() == "INVALID_ENTRIES_KEYS"
        validationErrors.get(0).defaultMessage() == "entry key cannot be empty"

        where:
        entryKey | _
        null     | _
        ""       | _
        " "      | _
    }
}
