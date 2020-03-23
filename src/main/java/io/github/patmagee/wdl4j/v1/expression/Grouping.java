package io.github.patmagee.wdl4j.v1.expression;

import java.util.Objects;

public class Grouping extends Expression {

    private final Expression innerExpression;

    public Grouping(Expression innerExpression) {
        Objects.requireNonNull(innerExpression,"The inner expression of a grouping cannot be null");
        this.innerExpression = innerExpression;
    }

    public Expression getInnerExpression() {
        return innerExpression;
    }
}
