package io.github.patmagee.wdl4j.v1.expression;

import lombok.NonNull;
import lombok.Value;

@Value
public class Signed extends Expression {

    @NonNull
    private final Expression expression;
    @NonNull
    private final Operation operation;

    public enum Operation {
        PLUS, MINUS
    }
}
