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
public class PairType extends Type {

    private final static Set<PairType> INSTANCES = new HashSet<>();
    @NonNull
    private final Type leftType;
    @NonNull
    private final Type rightType;

    public static PairType getType(Type leftType, Type rightType) {
        Objects.requireNonNull(leftType, "The leftType of a pair cannot be null");
        Objects.requireNonNull(rightType, "The rightType of a pair cannot be null");
        for (PairType instance : INSTANCES) {
            if (instance.leftType.equals(leftType) && instance.rightType.equals(rightType)) {
                return instance;
            }
        }

        PairType instance = new PairType(leftType, rightType);
        INSTANCES.add(instance);
        return instance;

    }

    @Override
    public String getTypeName() {
        return "Pair[" + leftType.getTypeName() + "," + rightType.getTypeName() + "]";
    }
}
