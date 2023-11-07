package dev.enginecode.eccommons.cqrs.command;

public interface CommandHandler<R, C extends Command<R>> {
    void handle(C command);
}
