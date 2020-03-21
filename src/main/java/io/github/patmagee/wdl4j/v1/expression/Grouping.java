package io.github.patmagee.wdl4j.v1.expression;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Grouping extends Expression {

    private Expression innerExpression;

}
