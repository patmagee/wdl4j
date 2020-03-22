package io.github.patmagee.wdl4j.v1.typing;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode
public class ArrayType implements Type {

    private Type innerType;
    private Boolean nonEmpty;

    private ArrayType(Type innerType, boolean nonEmpty) {
        this.innerType = innerType;
        this.nonEmpty = nonEmpty;
    }

    @Override
    public String getTypeName() {
        return "Array[" + innerType.getTypeName() + "]" + (nonEmpty ? "+" : "");
    }

    private final static Set<ArrayType> INSTANCES = new HashSet<>();

    public static ArrayType getType(@NonNull Type innerType, @NonNull boolean nonEmpty) {
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
