package io.github.patmagee.wdl4j.v1.typing;

import lombok.NonNull;

public class StringType extends Type {

    private static final StringType INSTANCE = new StringType();

    public static StringType getType() {
        return INSTANCE;
    }

    private StringType() {

    }

    @Override
    public boolean isCoercibleTo(@NonNull Type toType) {
        if (toType instanceof FileType || toType instanceof IntType || toType instanceof FloatType) {
            return true;
        }
        return super.isCoercibleTo(toType);
    }

    @Override
    public String getTypeName() {
        return "String";
    }
}
