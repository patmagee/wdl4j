package io.github.patmagee.wdl4j.v1.expression.literal;

import io.github.patmagee.wdl4j.v1.expression.Expression;

import java.util.Objects;

public class PairLiteral extends Expression {

    private final Expression leftValue;
    private final Expression rightValue;

    public PairLiteral(Expression leftValue, Expression rightValue) {
        Objects.requireNonNull(leftValue,"The \"left\" value of a pair cannot be null");
        Objects.requireNonNull(leftValue,"The \"right\" value of a pair cannot be null");
        this.leftValue = leftValue;
        this.rightValue = rightValue;
    }

    public Expression getLeftValue() {
        return leftValue;
    }

    public Expression getRightValue() {
        return rightValue;
    }
}
