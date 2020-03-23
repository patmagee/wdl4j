package io.github.patmagee.wdl4j.v1.expression;

import java.util.Objects;

public class TrueFalsePlaceholder extends Expression {

    private final Expression value;
    private final Condition condition;

    public TrueFalsePlaceholder(Expression value, Condition condition) {
        Objects.requireNonNull(value,"The value cannot be null for a TrueFalsePlaceholder");
        Objects.requireNonNull(condition,"The condition cannot be null for a TrueFalsePlaceholder");
        this.value = value;
        this.condition = condition;
    }

    public Expression getValue() {
        return value;
    }

    public Condition getCondition() {
        return condition;
    }

    public enum Condition {
        TRUE,FALSE
    }

}
