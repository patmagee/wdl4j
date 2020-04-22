package io.github.patmagee.wdl4j.v1.expression;

import lombok.NonNull;
import lombok.Value;

@Value
public class BinaryExpression extends Expression {

    @NonNull
    private final Expression leftHandSide;
    @NonNull
    private final Expression rightHandSide;
    @NonNull
    private final BinaryOperation operation;

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
