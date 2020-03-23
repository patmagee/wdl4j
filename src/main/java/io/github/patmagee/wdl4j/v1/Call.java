package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.WorkflowElement;
import io.github.patmagee.wdl4j.v1.expression.Expression;

import java.util.Map;
import java.util.Objects;

public class Call implements WorkflowElement {

    private final String taskName;
    private final String callAlias;
    private final Map<String, Expression> inputs;

    public static Builder newBuilder() {
        return new Builder();
    }

    public Call(String taskName, String callAlias, Map<String, Expression> inputs) {
        Objects.requireNonNull(taskName, "The task name of a call cannot be null");
        this.taskName = taskName;
        this.callAlias = callAlias;
        this.inputs = inputs;
    }

    private Call(Builder builder) {
        taskName = builder.taskName;
        callAlias = builder.callAlias;
        inputs = builder.inputs;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getCallAlias() {
        return callAlias;
    }

    public Map<String, Expression> getInputs() {
        return inputs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Call call = (Call) o;
        return taskName.equals(call.taskName) && Objects.equals(callAlias, call.callAlias) && Objects.equals(inputs,
                                                                                                             call.inputs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, callAlias, inputs);
    }

    public static final class Builder {

        private String taskName;
        private String callAlias;
        private Map<String, Expression> inputs;

        private Builder() {
        }

        public Builder withTaskName(String val) {
            taskName = val;
            return this;
        }

        public Builder withCallAlias(String val) {
            callAlias = val;
            return this;
        }

        public Builder withInputs(Map<String, Expression> val) {
            inputs = val;
            return this;
        }

        public Call build() {
            return new Call(this);
        }
    }
}
