package io.github.patmagee.wdl4j.v1.typing;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode
public class PairType implements Type {

    private Type leftType;
    private Type rightType;

    private PairType(Type leftType, Type rightType) {
        this.leftType = leftType;
        this.rightType = rightType;
    }

    @Override
    public String getTypeName() {
        return "Pair[" + leftType.getTypeName() + "," + rightType.getTypeName() + "]";
    }

    private final static Set<PairType> INSTANCES = new HashSet<>();

    public static PairType getType(@NonNull Type leftType,@NonNull Type rightType) {
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
