package io.github.patmagee.wdl4j.v1.typing;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode
public class OptionalType implements Type {

    private Type innerType;

    private OptionalType(Type innerType) {
        this.innerType = innerType;

    }

    private final static Set<OptionalType> INSTANCES = new HashSet<>();

    public static OptionalType getType(Type inner) {
        return INSTANCES.stream().filter(instance -> instance.innerType.equals(inner)).findFirst().orElseGet(() -> {
            OptionalType optionalType = new OptionalType(inner);
            INSTANCES.add(optionalType);
            return optionalType;
        });
    }

    @Override
    public String getTypeName() {
        return innerType.getTypeName() + "?";
    }
}
