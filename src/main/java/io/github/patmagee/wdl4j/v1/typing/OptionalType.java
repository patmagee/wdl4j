package io.github.patmagee.wdl4j.v1.typing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OptionalType extends Type {

    private final static Set<OptionalType> INSTANCES = new HashSet<>();
    @NonNull
    private final Type innerType;

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

    @Override
    public String getTypeName() {
        return innerType.getTypeName() + "?";
    }
}
