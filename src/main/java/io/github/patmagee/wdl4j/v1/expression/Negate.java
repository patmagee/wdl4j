package io.github.patmagee.wdl4j.v1.expression;

import java.util.Objects;

public class Negate extends Expression {

    private final Expression expression;

    public Negate(Expression expression) {
        Objects.requireNonNull(expression, "Negate expression cannot be null");
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }
}
