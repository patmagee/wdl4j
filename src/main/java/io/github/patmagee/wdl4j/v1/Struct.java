package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.WdlElement;

import java.util.List;
import java.util.Objects;

public class Struct implements WdlElement {

    private final String name;
    private final List<Declaration> members;

    public static Builder newBuilder() {
        return new Builder();
    }

    public Struct(String name, List<Declaration> members) {
        Objects.requireNonNull(name, "The name of a struct cannot be null");
        this.name = name;
        this.members = members;
    }

    private Struct(Builder builder) {
        name = builder.name;
        members = builder.members;
    }

    public String getName() {
        return name;
    }

    public List<Declaration> getMembers() {
        return members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Struct struct = (Struct) o;
        return name.equals(struct.name) && Objects.equals(members, struct.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, members);
    }

    public static final class Builder {

        private String name;
        private List<Declaration> members;

        private Builder() {
        }

        public Builder withName(String val) {
            name = val;
            return this;
        }

        public Builder withMembers(List<Declaration> val) {
            members = val;
            return this;
        }

        public Struct build() {
            return new Struct(this);
        }
    }

}
