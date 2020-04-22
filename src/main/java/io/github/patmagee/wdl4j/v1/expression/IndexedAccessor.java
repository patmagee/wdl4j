package io.github.patmagee.wdl4j.v1.expression;

import lombok.NonNull;
import lombok.Value;

@Value
public class IndexedAccessor extends Expression {

    @NonNull
    private final Expression element;
    @NonNull
    private final Expression expression;
}