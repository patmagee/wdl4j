package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.expression.Expression;

import java.util.Map;
import java.util.Objects;

public class ParameterMeta implements WdlElement {

    private Map<String, Expression> attributes;


    public static Builder newBuilder() {
        return new Builder();
    }

    public ParameterMeta(Map<String, Expression> attributes) {
        this.attributes = attributes;
    }

    private ParameterMeta(Builder builder) {
        attributes = builder.attributes;
    }

    public Map<String, Expression> getAttributes() {
        return attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParameterMeta that = (ParameterMeta) o;
        return Objects.equals(attributes, that.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributes);
    }

    public static final class Builder {

        private Map<String, Expression> attributes;

        private Builder() {
        }

        public Builder withAttributes(Map<String, Expression> val) {
            attributes = val;
            return this;
        }

        public ParameterMeta build() {
            return new ParameterMeta(this);
        }
    }


}
