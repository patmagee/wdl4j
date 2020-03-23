package io.github.patmagee.wdl4j.v1.expression;

import java.util.Objects;

public class IndexedAccessor extends Expression {

    private final Expression element;
    private final Expression expression;

    public IndexedAccessor(Expression element, Expression expression) {
        Objects.requireNonNull(element, "The expression element being accessed cannot be null");
        Objects.requireNonNull(expression, "The inner expression cannot be null");
        this.element = element;
        this.expression = expression;
    }

    public Expression getElement() {
        return element;
    }

    public Expression getExpression() {
        return expression;
    }
}
