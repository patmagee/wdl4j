package io.github.patmagee.wdl4j.v1.typing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Objects;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PairType extends Type {

    @NonNull
    private final Type leftType;
    @NonNull
    private final Type rightType;

    public static PairType getType(Type leftType, Type rightType) {
        Objects.requireNonNull(leftType, "The leftType of a pair cannot be null");
        Objects.requireNonNull(rightType, "The rightType of a pair cannot be null");
        return new PairType(leftType, rightType);

    }

    @Override
    public boolean isCoercibleTo(@NonNull Type toType) {
        if (toType instanceof PairType) {
            return rightType.isCoercibleTo(((PairType) toType).rightType) && leftType.isCoercibleTo(((PairType) toType).leftType);
        } else {
            return super.isCoercibleTo(toType);
        }
    }

    @Override
    public String getTypeName() {
        return "Pair[" + leftType.getTypeName() + "," + rightType.getTypeName() + "]";
    }
}
