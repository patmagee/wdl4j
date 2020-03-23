package io.github.patmagee.wdl4j.v1.expression;

import java.util.Objects;

public class DotAccessor extends Expression {

    private final Expression element;
    private final String name;

    public DotAccessor(Expression element, String name) {
        Objects.requireNonNull(element, "The expression for a dot accessor cannot be null");
        Objects.requireNonNull(name, "The name cannot for a dot accessor cannot be null");
        this.element = element;
        this.name = name;
    }

    public Expression getElement() {
        return element;
    }

    public String getName() {
        return name;
    }
}
