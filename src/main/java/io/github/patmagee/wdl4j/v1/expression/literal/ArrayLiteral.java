package io.github.patmagee.wdl4j.v1.expression.literal;

import io.github.patmagee.wdl4j.v1.expression.Expression;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
public class ArrayLiteral extends Expression {

    @NonNull
    private final List<Expression> values;

}
