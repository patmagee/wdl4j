package io.github.patmagee.wdl4j.v1.expression;

import java.util.Objects;

public class Signed extends Expression {

    private final Expression expression;
    private final Operation operation;

    public Signed(Expression expression, Operation operation) {
        Objects.requireNonNull(expression,"The expression to apply the sign to cannot be null");
        Objects.requireNonNull(operation,"The Signed operation cannot be null");
        this.expression = expression;
        this.operation = operation;
    }

    public Expression getExpression() {
        return expression;
    }

    public Operation getOperation() {
        return operation;
    }

    public enum Operation {
        PLUS,MINUS
    }
}
