package io.github.patmagee.wdl4j.v1.expression;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrueFalsePlaceholder extends Expression {

    private Expression value;
    private Condition condition;

    public enum Condition {
        TRUE,FALSE
    }

}