package dev.enginecode.eccommons.structures.model

import dev.enginecode.eccommons.exception.NotSupportedEntryException
import org.springframework.http.HttpStatus
import spock.lang.Specification

import static dev.enginecode.eccommons.exception.EngineCodeExceptionGroup.INFRASTRUCTURE_ERROR
import static dev.enginecode.eccommons.exception.NotSupportedEntryException.WRONG_TYPE

class EntryTest extends Specification {
    def "should not create entry for unknown type"() {
        given:
        def notSupportedType = "Not_existing_type"

        when:
        Entry<String>.of("DUMMY_KEY", "DUMMY_VALUE", notSupportedType, "DUMMY_INFO")

        then:
        def exception = thrown(NotSupportedEntryException)
        exception.getHttpErrorCode() == HttpStatus.INTERNAL_SERVER_ERROR.value()
        exception.getExceptionGroup() == INFRASTRUCTURE_ERROR.get()
        exception.getMessage() == String.format(WRONG_TYPE, notSupportedType)
    }
}
