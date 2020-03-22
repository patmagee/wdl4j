package io.github.patmagee.wdl4j.v1.typing;

import lombok.EqualsAndHashCode;
import lombok.Getter;

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

    private final static Set<PairType> INSTANCES = new HashSet<>();

    public static PairType getType(Type left, Type right) {
        return INSTANCES.stream()
                        .filter(a -> a.leftType.equals(left) && a.rightType.equals(right))
                        .findFirst()
                        .orElseGet(() -> {
                            PairType typeInstance = new PairType(left, right);
                            INSTANCES.add(typeInstance);
                            return typeInstance;
                        });

    }

    @Override
    public String getTypeName() {
        return "Pair[" + leftType.getTypeName() + "," + rightType.getTypeName() + "]";
    }
}
