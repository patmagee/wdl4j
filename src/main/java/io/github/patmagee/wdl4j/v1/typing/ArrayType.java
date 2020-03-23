package io.github.patmagee.wdl4j.v1.typing;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ArrayType extends Type {

    private final Type innerType;
    private final Boolean nonEmpty;

    private ArrayType(Type innerType, boolean nonEmpty) {
        this.innerType = innerType;
        this.nonEmpty = nonEmpty;
    }

    public Type getInnerType() {
        return innerType;
    }

    public Boolean getNonEmpty() {
        return nonEmpty;
    }

    @Override
    public String getTypeName() {
        return "Array[" + innerType.getTypeName() + "]" + (nonEmpty ? "+" : "");
    }


    private final static Set<ArrayType> INSTANCES = new HashSet<>();

    public static ArrayType getType(Type innerType, boolean nonEmpty) {
        Objects.requireNonNull(innerType, "The innertype of an ArrayType cannot be null");
        for (ArrayType instance : INSTANCES) {
            if (instance.innerType.equals(innerType) && nonEmpty == instance.nonEmpty) {
                return instance;
            }
        }

        ArrayType instance = new ArrayType(innerType, nonEmpty);
        INSTANCES.add(instance);
        return instance;
    }
}
