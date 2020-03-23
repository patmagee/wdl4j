package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.WorkflowElement;
import io.github.patmagee.wdl4j.v1.expression.Expression;

import java.util.List;
import java.util.Objects;

public class Conditional implements WorkflowElement {

    private final Expression expression;
    private final List<Declaration> declarations;
    private final List<WorkflowElement> elements;

    public Conditional(Expression expression, List<Declaration> declarations, List<WorkflowElement> elements) {
        Objects.requireNonNull(expression, "The expression of a Conditional workflow element cannot be null");
        this.expression = expression;
        this.declarations = declarations;
        this.elements = elements;
    }

    private Conditional(Builder builder) {
        expression = builder.expression;
        declarations = builder.declarations;
        elements = builder.elements;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Expression getExpression() {
        return expression;
    }

    public List<Declaration> getDeclarations() {
        return declarations;
    }

    public List<WorkflowElement> getElements() {
        return elements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Conditional that = (Conditional) o;
        return expression.equals(that.expression) && Objects.equals(declarations, that.declarations) && Objects.equals(
                elements,
                that.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression, declarations, elements);
    }

    public static final class Builder {

        private Expression expression;
        private List<Declaration> declarations;
        private List<WorkflowElement> elements;

        private Builder() {
        }

        public Builder withExpression(Expression val) {
            expression = val;
            return this;
        }

        public Builder withDeclarations(List<Declaration> val) {
            declarations = val;
            return this;
        }

        public Builder withElements(List<WorkflowElement> val) {
            elements = val;
            return this;
        }

        public Conditional build() {
            return new Conditional(this);
        }
    }

}
