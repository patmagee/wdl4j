package io.github.patmagee.wdl4j.v1.expression.literal;

import io.github.patmagee.wdl4j.v1.expression.Expression;
import lombok.Value;

@Value
public class IntLiteral extends Expression {

    private final int value;
}
