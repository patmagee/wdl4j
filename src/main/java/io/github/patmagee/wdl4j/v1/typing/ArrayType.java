package io.github.patmagee.wdl4j.v1.typing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.HashSet;
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
        return new ArrayType(innerType, nonEmpty);
    }

    public static ArrayType getType(Type innerType) {
        return new ArrayType(innerType, false);
    }

    @Override
    public boolean isCoercibleTo(@NonNull Type toType) {
        if (toType instanceof ArrayType) {
            ArrayType arrayToType = (ArrayType) toType;
            return (!arrayToType.nonEmpty || nonEmpty) && innerType.isCoercibleTo(arrayToType.innerType);
        } else if (toType instanceof StringType) {
            return innerType == null || innerType.isCoercibleTo(StringType.getType());
        }
        return super.isCoercibleTo(toType);
    }

    @Override
    public String getTypeName() {
        return "Array[" + innerType.getTypeName() + "]" + (nonEmpty ? "+" : "");
    }
}