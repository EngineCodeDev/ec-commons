package dev.enginecode.eccommons.structures.validation

import dev.enginecode.eccommons.structures.model.DataModel
import dev.enginecode.eccommons.structures.model.Entry
import spock.lang.Specification

class ContentsEntriesPayloadValidationStrategyTest extends Specification {
    private static final def dataModel = new DataModel(
            UUID.randomUUID(), Map.of() as LinkedHashMap,
            Map.of("Group 1", Set.of("Entry 1", "Entry 2"), "Group 2", Set.of("Entry 2", "Entry 3")) as LinkedHashMap,
            Map.of() as LinkedHashMap
    )

    private static final def strategy = new ContentsEntriesPayloadValidationStrategy()

    def "should validate entries payload"() {
        given:
        EntriesPayload entriesPayload = Mock(EntriesPayload)

        when:
        entriesPayload.groups() >> new LinkedHashSet<String>(List.of( "Group 1" ))
        entriesPayload.entries() >> new LinkedHashSet<Entry<?>>(Set.of(
                Entry.of("Entry 1", "", "string", ""),
                Entry.of("Entry 2", "", "string", ""),
                Entry.of("Entry 3", "", "string", "")
        ))
        def validationErrors = strategy.validate(entriesPayload, dataModel)

        then:
        validationErrors.size() == 1
        validationErrors.get(0).field() == "entries"
        validationErrors.get(0).errorCode() == "UNEXPECTED_CONTENTS"
        validationErrors.get(0).defaultMessage() == "[Entry 3] not expected for groups [Group 1]"

    }
}
