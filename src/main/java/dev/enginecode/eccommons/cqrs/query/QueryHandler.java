package dev.enginecode.eccommons.cqrs.query;

public interface QueryHandler <R, Q extends Query<R>>{
R handle (Q query);
}
