package io.github.patmagee.wdl4j.v1.expression;

import lombok.NonNull;
import lombok.Value;

@Value
public class DefaultPlaceholder extends Expression {

    @NonNull
    private final Expression value;

}
