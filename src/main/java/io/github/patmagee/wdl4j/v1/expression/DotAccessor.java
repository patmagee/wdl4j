package io.github.patmagee.wdl4j.v1.expression;

import lombok.NonNull;
import lombok.Value;

@Value
public class DotAccessor extends Expression {

    @NonNull
    private final Expression element;
    @NonNull
    private final String name;
}
