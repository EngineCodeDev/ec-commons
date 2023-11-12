package dev.enginecode.eccommons.infrastructure.json.errors;

import org.springframework.http.HttpStatus;

public enum EngineCodeErrors implements ErrorCode {
    //400
    //404
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND + "001"),
    //422
    CANNOT_DESERIALIZE_TO_GIVEN_CLASS(HttpStatus.UNPROCESSABLE_ENTITY + "001"),
    CANNOT_SERIALIZE_TO_JSON(HttpStatus.UNPROCESSABLE_ENTITY + "002"),
    //500
    DEFAULT_COMMON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR + "000"),
    TABLENAME_ANNOTATION_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR + "001"),
    TABLENAME_ANNOTATION_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR + "002"),
    //501
    CANNOT_SET_JSONB_TYPE(HttpStatus.INTERNAL_SERVER_ERROR.value() + "001");

    private final String engineCodeErrorId;

    EngineCodeErrors(String engineCodeErrorId) {
        this.engineCodeErrorId = engineCodeErrorId;
    }

    @Override
    public String getId() {
        return engineCodeErrorId;
    }
}
