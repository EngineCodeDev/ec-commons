package dev.enginecode.eccommons.structures.model;

import java.util.List;

public record Response<T>(List<T> items) {}
