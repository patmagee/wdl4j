package io.github.patmagee.wdl4j.v1.typing;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class PairType extends Type {

    private final Type leftType;
    private final Type rightType;

    private PairType(Type leftType, Type rightType) {
        this.leftType = leftType;
        this.rightType = rightType;
    }

    public Type getLeftType() {
        return leftType;
    }

    public Type getRightType() {
        return rightType;
    }

    @Override
    public String getTypeName() {
        return "Pair[" + leftType.getTypeName() + "," + rightType.getTypeName() + "]";
    }

    private final static Set<PairType> INSTANCES = new HashSet<>();

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
}
