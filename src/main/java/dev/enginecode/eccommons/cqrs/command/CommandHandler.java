package dev.enginecode.eccommons.cqrs.command;

public interface CommandHandler<R, C extends Command<R>> {
    R handle(C command);
}
