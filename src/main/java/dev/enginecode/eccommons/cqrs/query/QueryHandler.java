package dev.enginecode.eccommons.cqrs.query;

import dev.enginecode.eccommons.exception.EngineCodeException;

public interface QueryHandler<R, Q extends Query<R>> {
    R handle(Q query) throws EngineCodeException;
}
