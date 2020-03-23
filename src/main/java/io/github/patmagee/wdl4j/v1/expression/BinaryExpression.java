package io.github.patmagee.wdl4j.v1.expression;

import java.util.Objects;

public class BinaryExpression extends Expression {

    private final Expression leftHandSide;
    private final Expression rightHandSide;
    private final BinaryOperation operation;

    public BinaryExpression(Expression leftHandSide, Expression rightHandSide, BinaryOperation operation) {
        Objects.requireNonNull(leftHandSide,"Left hand side expresion cannot be null");
        Objects.requireNonNull(rightHandSide, "Right hand side expression cannot be null");
        Objects.requireNonNull(operation,"The Binary operation cannot be null");

        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
        this.operation = operation;
    }

    public Expression getLeftHandSide() {
        return leftHandSide;
    }

    public Expression getRightHandSide() {
        return rightHandSide;
    }

    public BinaryOperation getOperation() {
        return operation;
    }

    public enum BinaryOperation {
        ADD,

        DIVIDE,

        EQUAL_TO,

        GREATER_THAN,

        GREATER_THAN_OR_EQUAL,

        LESS_THAN,

        LESS_THAN_OR_EQUAL,

        LOGICAL_AND,

        LOGICAL_OR,

        MOD,

        MULTIPLY,

        NOT_EQUAL_TO,

        SUBTRACT
    }

}
