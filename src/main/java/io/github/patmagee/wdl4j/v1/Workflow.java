package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.api.WorkflowElement;

import java.util.List;
import java.util.Objects;

public class Workflow implements WdlElement {

    private final String name;
    private final Inputs inputs;
    private final List<Declaration> declarations;
    private final List<WorkflowElement> elements;
    private final Outputs outputs;
    private final Meta meta;
    private final ParameterMeta parameterMeta;

    public static Builder newBuilder() {
        return new Builder();
    }

    public Workflow(String name, Inputs inputs, List<Declaration> declarations, List<WorkflowElement> elements, Outputs outputs, Meta meta, ParameterMeta parameterMeta) {
        Objects.requireNonNull(name, "The workflow name cannot be null");
        this.name = name;
        this.inputs = inputs;
        this.declarations = declarations;
        this.elements = elements;
        this.outputs = outputs;
        this.meta = meta;
        this.parameterMeta = parameterMeta;
    }

    private Workflow(Builder builder) {
        name = builder.name;
        inputs = builder.inputs;
        declarations = builder.declarations;
        elements = builder.elements;
        outputs = builder.outputs;
        meta = builder.meta;
        parameterMeta = builder.parameterMeta;
    }

    public String getName() {
        return name;
    }

    public Inputs getInputs() {
        return inputs;
    }

    public List<Declaration> getDeclarations() {
        return declarations;
    }

    public List<WorkflowElement> getElements() {
        return elements;
    }

    public Outputs getOutputs() {
        return outputs;
    }

    public Meta getMeta() {
        return meta;
    }

    public ParameterMeta getParameterMeta() {
        return parameterMeta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Workflow workflow = (Workflow) o;
        return name.equals(workflow.name) && Objects.equals(inputs, workflow.inputs) && Objects.equals(declarations,
                                                                                                       workflow.declarations) && Objects
                       .equals(elements, workflow.elements) && Objects.equals(outputs,
                                                                              workflow.outputs) && Objects.equals(meta,
                                                                                                                  workflow.meta) && Objects
                       .equals(parameterMeta, workflow.parameterMeta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, inputs, declarations, elements, outputs, meta, parameterMeta);
    }

    public static final class Builder {

        private String name;
        private Inputs inputs;
        private List<Declaration> declarations;
        private List<WorkflowElement> elements;
        private Outputs outputs;
        private Meta meta;
        private ParameterMeta parameterMeta;

        private Builder() {
        }

        public Builder withName(String val) {
            name = val;
            return this;
        }

        public Builder withInputs(Inputs val) {
            inputs = val;
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

        public Builder withOutputs(Outputs val) {
            outputs = val;
            return this;
        }

        public Builder withMeta(Meta val) {
            meta = val;
            return this;
        }

        public Builder withParameterMeta(ParameterMeta val) {
            parameterMeta = val;
            return this;
        }

        public Workflow build() {
            return new Workflow(this);
        }
    }
}
