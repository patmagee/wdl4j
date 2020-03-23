package io.github.patmagee.wdl4j.v1.expression;

import java.util.Objects;

public class DefaultPlaceholder extends Expression {

    private final Expression value;

    public DefaultPlaceholder(Expression value) {
        Objects.requireNonNull(value,"Default placeholder value cannot be null");
        this.value = value;
    }

    public Expression getValue() {
        return value;
    }
}
