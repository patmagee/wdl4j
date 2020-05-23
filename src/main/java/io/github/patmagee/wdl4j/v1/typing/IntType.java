package io.github.patmagee.wdl4j.v1.typing;

import lombok.NonNull;

public class IntType extends Type {

    private static final IntType INSTANCE = new IntType();

    public static IntType getType() {
        return INSTANCE;
    }

    private IntType() {

    }

    @Override
    public boolean isCoercibleTo(@NonNull Type toType) {
        if (toType instanceof StringType || toType instanceof FloatType){
            return true;
        } else  {
            return super.isCoercibleTo(toType);
        }
    }

    @Override
    public String getTypeName() {
        return "Int";
    }
}
