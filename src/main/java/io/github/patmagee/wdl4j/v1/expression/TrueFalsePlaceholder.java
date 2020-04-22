package io.github.patmagee.wdl4j.v1.expression;

import lombok.NonNull;
import lombok.Value;

@Value
public class TrueFalsePlaceholder extends Expression {

    @NonNull
    private final Expression value;
    @NonNull
    private final Condition condition;

    public enum Condition {
        TRUE, FALSE
    }

}
