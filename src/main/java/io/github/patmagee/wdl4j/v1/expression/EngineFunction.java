package io.github.patmagee.wdl4j.v1.expression;

import java.util.List;
import java.util.Objects;

public class EngineFunction extends Expression {

    private String name;
    private List<Expression> arguments;

    public EngineFunction(String name, List<Expression> arguments) {
        Objects.requireNonNull(name, "Engine function name cannot be null");
        this.name = name;
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public List<Expression> getArguments() {
        return arguments;
    }
}
