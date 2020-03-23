package io.github.patmagee.wdl4j.v1.expression;

public class SepPlaceholder extends Expression {

    private final Expression value;

    public SepPlaceholder(Expression value) {
        this.value = value;
    }

    public Expression getValue() {
        return value;
    }
}
