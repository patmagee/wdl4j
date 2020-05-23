package io.github.patmagee.wdl4j.v1.typing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OptionalType extends Type {

    @NonNull
    private final Type innerType;

    public static OptionalType getType(Type innerType) {
        return new OptionalType(innerType);
    }

    @Override
    public boolean isCoercibleTo(@NonNull Type toType) {
        if (toType instanceof OptionalType) {
            return innerType.isCoercibleTo(((OptionalType) toType).innerType);
        } else {
            return false;
        }
    }

    @Override
    public String getTypeName() {
        return innerType.getTypeName() + "?";
    }
}
