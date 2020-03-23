package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.WdlElement;

import java.util.List;
import java.util.Objects;

public class Inputs implements WdlElement {

    private final List<Declaration> declarations;


    public static Builder newBuilder() {
        return new Builder();
    }

    public Inputs(List<Declaration> declarations) {
        this.declarations = declarations;
    }

    private Inputs(Builder builder) {
        declarations = builder.declarations;
    }

    public List<Declaration> getDeclarations() {
        return declarations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Inputs inputs = (Inputs) o;
        return Objects.equals(declarations, inputs.declarations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(declarations);
    }

    public static final class Builder {

        private List<Declaration> declarations;

        private Builder() {
        }

        public Builder withDeclarations(List<Declaration> val) {
            declarations = val;
            return this;
        }

        public Inputs build() {
            return new Inputs(this);
        }
    }

}
