package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.WdlElement;

import java.util.List;
import java.util.Objects;

public class Task implements WdlElement {

    private final String name;
    private final Inputs inputs;
    private final List<Declaration> declarations;
    private final Command command;
    private final Runtime runtime;
    private final Outputs outputs;
    private final Meta meta;
    private final ParameterMeta parameterMeta;

    public static Builder newBuilder() {
        return new Builder();
    }

    public Task(String name, Inputs inputs, List<Declaration> declarations, Command command, Runtime runtime, Outputs outputs, Meta meta, ParameterMeta parameterMeta) {
        Objects.requireNonNull(name, "The task name cannot be null");
        this.name = name;
        this.inputs = inputs;
        this.declarations = declarations;
        this.command = command;
        this.runtime = runtime;
        this.outputs = outputs;
        this.meta = meta;
        this.parameterMeta = parameterMeta;
    }

    private Task(Builder builder) {
        name = builder.name;
        inputs = builder.inputs;
        declarations = builder.declarations;
        command = builder.command;
        runtime = builder.runtime;
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

    public Command getCommand() {
        return command;
    }

    public Runtime getRuntime() {
        return runtime;
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
        Task task = (Task) o;
        return name.equals(task.name) && Objects.equals(inputs, task.inputs) && Objects.equals(declarations,
                                                                                               task.declarations) && Objects
                       .equals(command, task.command) && Objects.equals(runtime,
                                                                        task.runtime) && Objects.equals(outputs,
                                                                                                        task.outputs) && Objects
                       .equals(meta, task.meta) && Objects.equals(parameterMeta, task.parameterMeta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, inputs, declarations, command, runtime, outputs, meta, parameterMeta);
    }

    public static final class Builder {

        private String name;
        private Inputs inputs;
        private List<Declaration> declarations;
        private Command command;
        private Runtime runtime;
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

        public Builder withCommand(Command val) {
            command = val;
            return this;
        }

        public Builder withRuntime(Runtime val) {
            runtime = val;
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

        public Task build() {
            return new Task(this);
        }
    }

}
