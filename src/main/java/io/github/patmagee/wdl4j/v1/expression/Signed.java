package io.github.patmagee.wdl4j.v1.expression;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Signed extends Expression {

    private Operation operation;
    private Expression expression;

    public enum Operation {
        PLUS,MINUS
    }
}
