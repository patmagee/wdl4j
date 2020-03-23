package io.github.patmagee.wdl4j.v1.expression;

import java.util.Objects;

public class VariableReference extends Expression {

    private final String name;

    public VariableReference(String name) {
        Objects.requireNonNull(name, "The name cannot be null for a VariableReference");
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
