package io.github.patmagee.wdl4j.v1.expression;

import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
public class EngineFunction extends Expression {

    @NonNull
    private final String name;
    @NonNull
    private final List<Expression> arguments;

}
