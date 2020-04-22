package io.github.patmagee.wdl4j.v1.expression;

import lombok.NonNull;
import lombok.Value;

@Value
public class IfThenElse extends Expression {

    @NonNull
    private final Expression condition;
    @NonNull
    private final Expression ifTrue;
    @NonNull
    private final Expression ifFalse;

}
