package io.github.patmagee.wdl4j.v1.typing;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode
public class OptionalType implements Type {

    private Type innerType;

    private OptionalType(Type innerType) {
        this.innerType = innerType;

    }

    @Override
    public String getTypeName() {
        return innerType.getTypeName() + "?";
    }

    private final static Set<OptionalType> INSTANCES = new HashSet<>();

    public static OptionalType getType(@NonNull Type innerType) {
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
