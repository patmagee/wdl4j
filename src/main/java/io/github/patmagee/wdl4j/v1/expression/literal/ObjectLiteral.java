package io.github.patmagee.wdl4j.v1.expression.literal;

import io.github.patmagee.wdl4j.v1.expression.Expression;
import lombok.NonNull;
import lombok.Value;

import java.util.Map;

@Value
public class ObjectLiteral extends Expression {

    @NonNull
    private final Map<String, Expression> values;
}
