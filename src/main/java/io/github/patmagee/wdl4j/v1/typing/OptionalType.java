package io.github.patmagee.wdl4j.v1.typing;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class OptionalType extends Type {

    private final Type innerType;

    private OptionalType(Type innerType) {
        this.innerType = innerType;

    }

    public Type getInnerType() {
        return innerType;
    }

    @Override
    public String getTypeName() {
        return innerType.getTypeName() + "?";
    }


    private final static Set<OptionalType> INSTANCES = new HashSet<>();

    public static OptionalType getType(Type innerType) {
        Objects.requireNonNull(innerType, "InnerType of an optional cannot be null");
        for (OptionalType instance : INSTANCES) {
            if (instance.innerType.equals(innerType)) {
                return instance;
            }
        }

        OptionalType instance = new OptionalType(innerType);
        INSTANCES.add(instance);
        return instance;
    }
}
