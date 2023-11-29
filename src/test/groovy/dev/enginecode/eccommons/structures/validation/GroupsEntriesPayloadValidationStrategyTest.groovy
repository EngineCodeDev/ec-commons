package dev.enginecode.eccommons.structures.validation

import dev.enginecode.eccommons.structures.model.DataModel
import spock.lang.Specification

class GroupsEntriesPayloadValidationStrategyTest extends Specification {
    private static final def dataModel = new DataModel(
            UUID.randomUUID(), Map.of() as LinkedHashMap, Map.of("Known group", Set.of()) as LinkedHashMap,
            Map.of() as LinkedHashMap
    )

    private static final def strategy = new GroupsEntriesPayloadValidationStrategy()

    def "should validate entries payload"() {
        given:
        EntriesPayload entriesPayload = Mock(EntriesPayload)

        when:
        entriesPayload.groups() >> new LinkedHashSet<String>(List.of( "Unknown", "Known group", "Unknown 2" ))
        def validationErrors = strategy.validate(entriesPayload, dataModel)

        then:
        validationErrors.size() == 1
        validationErrors.get(0).field() == "groups"
        validationErrors.get(0).errorCode() == "UNKNOWN_GROUPS"
        validationErrors.get(0).defaultMessage() == "[Unknown, Unknown 2] not found"

    }
}
