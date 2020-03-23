package io.github.patmagee.wdl4j.v1.expression.literal;

import io.github.patmagee.wdl4j.v1.expression.Expression;

public class FloatLiteral extends Expression {

    private final float value;

    public FloatLiteral(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }
}
