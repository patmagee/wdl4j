package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.WorkflowElement;
import io.github.patmagee.wdl4j.v1.expression.Expression;

import java.util.List;
import java.util.Objects;

public class Scatter implements WorkflowElement {

    private final String varname;
    private final Expression expression;
    private final List<Declaration> declarations;
    private final List<WorkflowElement> workflowElements;

    public static Builder newBuilder() {
        return new Builder();
    }

    public Scatter(String varname, Expression expression, List<Declaration> declarations, List<WorkflowElement> workflowElements) {
        Objects.requireNonNull(varname, "The scatter varname cannot be null");
        Objects.requireNonNull(expression, "The scatter expression cannot be null");
        this.varname = varname;
        this.expression = expression;
        this.declarations = declarations;
        this.workflowElements = workflowElements;
    }

    private Scatter(Builder builder) {
        varname = builder.varname;
        expression = builder.expression;
        declarations = builder.declarations;
        workflowElements = builder.workflowElements;
    }

    public String getVarname() {
        return varname;
    }

    public Expression getExpression() {
        return expression;
    }

    public List<Declaration> getDeclarations() {
        return declarations;
    }

    public List<WorkflowElement> getWorkflowElements() {
        return workflowElements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Scatter scatter = (Scatter) o;
        return varname.equals(scatter.varname) && expression.equals(scatter.expression) && Objects.equals(declarations,
                                                                                                          scatter.declarations) && Objects
                       .equals(workflowElements, scatter.workflowElements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(varname, expression, declarations, workflowElements);
    }

    public static final class Builder {

        private String varname;
        private Expression expression;
        private List<Declaration> declarations;
        private List<WorkflowElement> workflowElements;

        private Builder() {
        }

        public Builder withVarname(String val) {
            varname = val;
            return this;
        }

        public Builder withExpression(Expression val) {
            expression = val;
            return this;
        }

        public Builder withDeclarations(List<Declaration> val) {
            declarations = val;
            return this;
        }

        public Builder withWorkflowElements(List<WorkflowElement> val) {
            workflowElements = val;
            return this;
        }

        public Scatter build() {
            return new Scatter(this);
        }
    }

}
