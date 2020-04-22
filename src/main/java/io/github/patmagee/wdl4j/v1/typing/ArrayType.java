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
public class ArrayType extends Type {

    private final static Set<ArrayType> INSTANCES = new HashSet<>();
    @NonNull
    private final Type innerType;
    @NonNull
    private final Boolean nonEmpty;

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

    @Override
    public String getTypeName() {
        return "Array[" + innerType.getTypeName() + "]" + (nonEmpty ? "+" : "");
    }
}
