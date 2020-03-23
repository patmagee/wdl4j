package io.github.patmagee.wdl4j.v1.expression;

import java.util.Objects;

public class IfThenElse extends Expression {

    private Expression condition;
    private Expression ifTrue;
    private Expression ifFalse;

    public IfThenElse(Expression condition, Expression ifTrue, Expression ifFalse) {
        Objects.requireNonNull(condition, "The condition for an if-then-else statement cannot be null");
        Objects.requireNonNull(ifTrue, "The expression if true for an if-then-else statement cannot be null");
        Objects.requireNonNull(condition, "The expression if false for an if-then-else statement cannot be null");
        this.condition = condition;
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
    }

    public Expression getCondition() {
        return condition;
    }

    public Expression getIfTrue() {
        return ifTrue;
    }

    public Expression getIfFalse() {
        return ifFalse;
    }
}
